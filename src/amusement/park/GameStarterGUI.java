package amusement.park;

import javax.swing.*;
import java.awt.*;

import static amusement.park.GameGUI.SCREEN_SIZE;

public class GameStarterGUI {
    
     public GameStarterGUI() {
        // initialization
        JFrame frame = new JFrame();
        JPanel buttonPanel = new JPanel();
        JLabel gameNameLabel = new JLabel();
        JLabel gamePicLabel = new JLabel();

        // Setting up the pic label appearance
//        gamePicLabel.setIcon(new ImageIcon(new ImageIcon("images/Castle.png").getImage().
//                getScaledInstance(350, 350, Image.SCALE_DEFAULT)));
        gamePicLabel.setHorizontalAlignment(JLabel.CENTER);

        // Setting up the name label appearance
        gameNameLabel.setText("Park Simulator");
        gameNameLabel.setFont(new Font("Serif", Font.PLAIN, 55));
        gameNameLabel.setHorizontalAlignment(JLabel.CENTER);

        // Setting up the start frame

        // Adding the button panel
        frame.getContentPane().add(gamePicLabel, BorderLayout.NORTH);
        frame.getContentPane().add(gameNameLabel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Setting up the buttons action listeners
        JButton startButton = new JButton("Start");
        setupButtonAppearance(startButton);
        startButton.addActionListener(actionEvent -> {
            new GameGUI();
            frame.dispose();
        });

        JButton exitButton = new JButton("Exit");
        setupButtonAppearance(exitButton);
        exitButton.addActionListener(actionEvent -> System.exit(0));
        buttonPanel.add(BorderLayout.NORTH, startButton);
        buttonPanel.add(BorderLayout.CENTER, exitButton);

        // Basic settings for the frame
        setupFrameSettings(frame);

        //Should be here to avoid the not appearing bug
        setupFrameAppearance(frame);
        
    }

    /**
     * Setting the basic settings for the frame
     * @param f
     */
    private void setupFrameSettings(JFrame f){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height));
        f.pack();
    }


    /**
     * Setting the general appearance for the frame
     * @param f
     */
    private void setupFrameAppearance(JFrame f){
        f.setVisible(true);
        f.setResizable(false);
        f.setSize(new Dimension(1300, 600));
    }

    /**
     * Setting up buttons appearance
     * @param b
     */
    private void setupButtonAppearance(JButton b){
        b.setPreferredSize(new Dimension(120, 100));
        b.setBackground(new Color(0, 0, 0));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Tahoma", Font.BOLD, 16));
    }
}
