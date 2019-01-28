package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.ArrayList;

public class Minesweeper extends Frame {
    private Panel infoMenu;
    private Button resetBtn;
    private Label marksRemainLabel;
    private Label timeLabel;
    private Panel board;
    private Target[][] targetLayout;
    private boolean[][] mineLayout;
    private int[][] mineNumbers;
    private ArrayList<Target> swapTargets;
    private int rowTotal;
    private int colTotal;
    private int numMines;
    private int minesMarked = 0;
    private int targetsClicked = 0;
    private Timer timer;
    private int time = 0;
    
    enum Result {
        WIN,
        LOSE
    }

    public Minesweeper() {
        initComponents();
    }
    
    private void initComponents() {
        //GENERAL APPLICATION & GAME SETUP
        rowTotal = 10;
        colTotal = 10;
        numMines = 10;
        final Dimension TARGET_SIZE = new Dimension(25, 25);
        final int boardMargin = 100;
        Dimension boardSize = new Dimension(TARGET_SIZE.width * colTotal, TARGET_SIZE.height * rowTotal);   
        Dimension infoMenuSize = new Dimension(boardSize.width, 40);
        final int APP_WIDTH = boardSize.width + boardMargin;
        final int APP_HEIGHT = boardSize.height + infoMenuSize.height + boardMargin;
 
        setSize(APP_WIDTH, APP_HEIGHT);
        setLocationRelativeTo(null); //center on screen
        setLayout(new FlowLayout());
        setResizable(false);
        setTitle("Minesweeper");
 
        //INFO MENU
        infoMenu = new Panel();
        infoMenu.setPreferredSize(infoMenuSize);
        infoMenu.setLayout(new FlowLayout(FlowLayout.CENTER, infoMenuSize.width / 10, 0));
        add(infoMenu);
        
        resetBtn = new Button("Reset");
        resetBtn.setFocusable(false);
        marksRemainLabel = new Label(String.format("%3s", String.valueOf(numMines)).replace(' ', '0'), Label.CENTER);
        marksRemainLabel.setBackground(Color.black);
        marksRemainLabel.setForeground(Color.red);
        marksRemainLabel.setFont(new Font("", Font.BOLD, 20));
        timeLabel = new Label("000", Label.CENTER);
        timeLabel.setBackground(Color.black);
        timeLabel.setForeground(Color.red);
        timeLabel.setFont(new Font("", Font.BOLD, 20));   
        infoMenu.add(marksRemainLabel);
        infoMenu.add(resetBtn);
        infoMenu.add(timeLabel);

        //Button click handler to reset game
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new Minesweeper();
            }
        });
        
        //GAME BOARD
        FlowLayout boardLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        board = new Panel(boardLayout);
        board.setPreferredSize(boardSize);
        add(board);
                
        setMines();
        surroundingMines();
        
        targetLayout = new Target[rowTotal][colTotal];
        swapTargets = new ArrayList<Target>();
        for (int row = 0; row < rowTotal; row++) {
            for (int col = 0; col < colTotal; col++) {
                Target target = new Target(this, board, row, col, mineLayout[row][col], mineNumbers[row][col]);
                target.setPreferredSize(TARGET_SIZE);
                targetLayout[row][col] = target;
                board.add(target);
                
                if (!mineLayout[row][col]) swapTargets.add(target);
            }
        }
        
        for (int i = 0; i < 3; i++) {
            shuffleSwapTargets();
        }
                
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        setVisible(true);
    }
    
    //Method to set mines on game board
    private void setMines() {
        mineLayout = new boolean[rowTotal][colTotal];
        
        for (int row = 0, targetCount = 0; row < rowTotal; row++) {
            for (int col = 0; col < colTotal; col++, targetCount++) {
                mineLayout[row][col] = targetCount < numMines;
            }
        }
        
        for (int i = 0; i < 3; i++) {
            shuffleMines();
        }
    }
    
    //Method to shuffle mines within game board
    private void shuffleMines() {
        Random random = new Random();
        int swapRow, swapCol;
        boolean temp;

        for (int row = 0; row < rowTotal; row++) {
            for (int col = 0; col < colTotal; col++) {
                swapRow = random.nextInt(rowTotal);
                swapCol = random.nextInt(colTotal);
                temp = mineLayout[row][col];
                mineLayout[row][col] = mineLayout[swapRow][swapCol];
                mineLayout[swapRow][swapCol] = temp;
            }
        }
    }
    
    //Method to shuffle entries in non-mine swap target array
    private void shuffleSwapTargets() {
        Random random = new Random();
        int swap;
        int numSwaps = swapTargets.size();
        Target temp;
        
        for (int i = 0; i < numSwaps; i++) {
            swap = random.nextInt(numSwaps);
            temp = swapTargets.get(i);
            swapTargets.set(i, swapTargets.get(swap));
            swapTargets.set(swap, temp);
        }
    }
            
    //Method to assign surrounding mine numbers
    private void surroundingMines() {
        mineNumbers = new int[rowTotal][colTotal];
        int count;
        
        for (int row = 0; row < rowTotal; row++) {
            for (int col = 0; col < colTotal; col++) {
                count = 0;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i != -1 && i != rowTotal && j != -1 && j != colTotal
                                && !(i == row && j == col))
                            if (mineLayout[i][j]) count++;
                    }
                }
              
                mineNumbers[row][col] = count;
            }
        }
    }
    
    //Method to set timer
    public void setTimer() {
        timer = new Timer();
        
        TimerTask tick = new TimerTask() {
            @Override
            public void run() {
                time++;
                if (time >= 999)
                    timeLabel.setText("999");
                else
                    timeLabel.setText(String.format("%3s", String.valueOf(time)).replace(' ', '0'));
            }
        };
        
        timer.scheduleAtFixedRate(tick, 1000, 1000);
    }
    
    //Method to stop timer
    public void stopTimer() {
        timer.cancel();
    }
    
    //Getters and setters
    public int getNumTargets() { return rowTotal * colTotal; } 
    public int getNumMines() { return numMines; }
    public int getTargetsClicked() { return targetsClicked; }
    public void setTargetsClicked() { targetsClicked++; }
    public int getMinesMarked() { return minesMarked; }
    public void setMinesMarked(int i) {
        minesMarked += i;
        if (numMines - minesMarked < 0)
            marksRemainLabel.setText(String.valueOf(numMines - minesMarked));
        else
            marksRemainLabel.setText(String.format("%3s", String.valueOf(numMines - minesMarked)).replace(' ', '0'));
    }
    public Target[][] getTargetLayout() { return targetLayout; }  
    public ArrayList<Target> getSwapTargets() { return swapTargets; }
    
    public static void main(String[] args) {
        new Minesweeper();
    }
}