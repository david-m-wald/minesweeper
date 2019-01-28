package minesweeper;

import java.awt.*;
import java.awt.event.*;

public class WinLoseDialog extends Dialog {
    private Button replayBtn;
    private Button closeBtn;

    public WinLoseDialog(Minesweeper application, Minesweeper.Result result) {
        super(application, true);

        if (result == Minesweeper.Result.WIN)
            setTitle("You win!!!");
        else if (result == Minesweeper.Result.LOSE)
            setTitle("You lose.");

        setLayout(new FlowLayout());
        setSize(new Dimension(200, 100));
        setLocation(application.getX() + (application.getWidth() - getWidth()) / 2,
                application.getY() + (application.getHeight() - getHeight()) / 2);     
        setResizable(false);

        replayBtn = new Button("Replay");
        closeBtn = new Button("Close");
        add(replayBtn);
        add(closeBtn);

        //Close button click handler
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        //Replay button click handler
        replayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                application.dispose();
                new Minesweeper();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                Panel board = (Panel)application.getComponent(1); //Game board currently at component index 1
                board.setEnabled(false);
            }
        });

        setVisible(true);
    }
}