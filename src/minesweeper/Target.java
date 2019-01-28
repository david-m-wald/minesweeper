package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.ArrayList;

public class Target extends Button {
    private Panel board;
    private Minesweeper application;
    private int row;
    private int col;
    private boolean mine;
    private int surrounding;
    private boolean clicked = false;
    public OnClick clickHandler;
        
    public Target(Minesweeper application, Panel board, int row, int col, boolean mine, int surrounding) {
        this.application = application;
        this.board = board;
        this.row = row;
        this.col = col;
        this.mine = mine;
        this.surrounding = surrounding;
        setFocusable(false);
        setFont(new Font("", Font.BOLD, 20));   
        clickHandler = new OnClick();
        addActionListener(clickHandler);
        addMouseListener(clickHandler);
    }
    
    //Regular left click handler
    private class OnClick extends MouseAdapter implements ActionListener {
        /*Display value, remove existing event handlers, add new combo-click handler, and test
          win/lose/continue condition*/
        @Override
        public void actionPerformed(ActionEvent e) {
            clicked = true;
            
            //Adjust game board if player hits mine or target with surrounding mines on first move
            if (application.getTargetsClicked() == 0 && (mine || surrounding != 0)) 
                adjustBoard();
            
            if (application.getTargetsClicked() == 0)
                application.setTimer();
            
            application.setTargetsClicked();
            setLabel(mine ? "*" : (surrounding == 0 ? "" : String.valueOf(surrounding)));
            
            if (!mine) {
                displayColor();
                if (application.getTargetsClicked() == application.getNumTargets() - application.getNumMines()) {
                    application.stopTimer();
                    new WinLoseDialog(application, Minesweeper.Result.WIN);
                }
                removeActionListener(this);
                removeMouseListener(this);
                if (surrounding == 0)
                    autoClick();
                else
                    addMouseListener(new ComboClick());
            } else {
                application.stopTimer();
                setBackground(Color.red);
                new WinLoseDialog(application, Minesweeper.Result.LOSE);            
            }
        }
        
        /*Right click and release over target to mark/unmark mine, removing regular click
          handler if target is marked*/        
        @Override
        public void mouseReleased(MouseEvent e)
        {
            if (e.getButton() == MouseEvent.BUTTON3 && contains(e.getPoint())) {
                if (getLabel() == "") {
                    application.setMinesMarked(1);
                    setLabel("?");
                    setBackground(Color.pink);
                    removeActionListener(this);
                } else {
                    application.setMinesMarked(-1);
                    setLabel("");
                    setBackground(new Color(240, 240, 240));
                    addActionListener(this);
                }
            }
        }
    }
    
    //Combo click handler for valid "zone click"
    private class ComboClick extends MouseAdapter {     
        private MouseAdapter combo;
        
        /*Left and right mouse buttons pressed one after other over diffused target with correct
          # of surrounding mines marked followed by release of either button over target*/
        @Override
        public void mousePressed(MouseEvent e) {          
            int mouseButtonCombo = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
            
            if (((e.getModifiersEx() & mouseButtonCombo) == mouseButtonCombo)
                && contains(e.getPoint()) && surrounding == countMineMarks()) {
                combo = new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        int release = e.getButton();
                        if (release == MouseEvent.BUTTON1 || release == MouseEvent.BUTTON3) {
                            if (contains(e.getPoint())) autoClick();
                            removeMouseListener(combo);
                        }
                    } 
                };
                addMouseListener(combo);
            }
        }
    }
    
    //Method to update game board if player hits mine or target with surrounding mines on first move
    private void adjustBoard() {
        Target[][] targetLayout = application.getTargetLayout();
        ArrayList<Target> swapTargets = application.getSwapTargets();
        int rowTotal = targetLayout.length;
        int colTotal = targetLayout[0].length;
        int swapNumber = 0;
        
        //Remove current and surrounding targets from swap list
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal)
                    swapTargets.remove(targetLayout[i][j]);
            }
        }
        
        //Swap current/surrounding mine targets with swap list targets
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                    && targetLayout[i][j].mine)
                    swap(targetLayout[i][j], swapTargets.get(swapNumber++));
            }
        }
    }
    
    //Method to swap a mine target and non-mine target
    private void swap(Target removeMine, Target addMine) {
        Target[][] targetLayout = application.getTargetLayout();
        int rowTotal = targetLayout.length;
        int colTotal = targetLayout[0].length;
        
        //Remove mine from target and decrease mine number of all surrounding targets
        removeMine.mine = false;
        for (int i = removeMine.row - 1; i <= removeMine.row + 1; i++) {
            for (int j = removeMine.col - 1; j <= removeMine.col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                    && !(i == removeMine.row && j == removeMine.col))
                targetLayout[i][j].surrounding--;
            }
        }        
        
        //Add mine to target and increase mine number of all surrounding targets
        addMine.mine = true;
        for (int i = addMine.row - 1; i <= addMine.row + 1; i++) {
            for (int j = addMine.col - 1; j <= addMine.col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                    && !(i == addMine.row && j == addMine.col))
                targetLayout[i][j].surrounding++;
            }
        }
    }  

    /*Method to "auto-click" on surrounding unclicked and unmarked targets for/when:
        1) "zero-mine click" - current clicked target has no surrounding mines
        2) "zone click" - combo-clicked defused target with correct # of surrounding mine marks*/
    private void autoClick() {
        Target[][] targetLayout = application.getTargetLayout();
        int rowTotal = targetLayout.length;
        int colTotal = targetLayout[0].length;
    
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                    && !(i == row && j == col))
                    if (!targetLayout[i][j].clicked && targetLayout[i][j].getLabel() != "?")
                        targetLayout[i][j].clickHandler.actionPerformed(null);
            } 
        }
    }
    
    //Method to count surrounding mine marks
    private int countMineMarks() {
        Target[][] targetLayout = application.getTargetLayout();
        int rowTotal = targetLayout.length;
        int colTotal = targetLayout[0].length;
        int count = 0;
    
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                    && !(i == row && j == col))
                    if (targetLayout[i][j].getLabel() == "?") count++;
            } 
        }
        
        return count;
    }    
    
    //Method to display color for mine numbers
    private void displayColor() {
        setBackground(new Color(200, 200, 200));
        switch(surrounding)
        {
            case 1:
                setForeground(Color.blue);
                break;
            case 2:
                setForeground(new Color(0, 110, 0));
                break;
            case 3:
                setForeground(Color.red);
                break;
            case 4:
                setForeground(new Color(0, 0, 150));
                break;
            case 5:
                setForeground(new Color(100, 0, 0));
                break;
            case 6:
                setForeground(new Color(0, 150, 150));
                break;
            case 7:
                setForeground(Color.orange);
                break;
            default:
                setForeground(Color.black);
                break;
        }
    }  
}
