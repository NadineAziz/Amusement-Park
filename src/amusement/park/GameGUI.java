package amusement.park;


import javax.swing.*;
import java.awt.*;

public class GameGUI {
    private static final Font font = new Font("SansSerif", Font.BOLD, 18);
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public GameGUI() {
        JFrame frame = new JFrame();


        setupFrameSettings(frame);

        // Setting the top panel
        CoinsPanel coinsPanel = new CoinsPanel(); 
        
        GamePanel engine = new GamePanel(500, coinsPanel);
        engine.setBackground(Color.GRAY);

        frame.getContentPane().add(BorderLayout.NORTH, coinsPanel);
        frame.getContentPane().add(BorderLayout.CENTER, engine);

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
        f.setSize(new Dimension(1300, 800));
    }
}
