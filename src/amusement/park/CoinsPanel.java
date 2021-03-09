/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

import static amusement.park.GameGUI.SCREEN_SIZE;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Family
 */
public class CoinsPanel extends JPanel {
    
    private static final Font font = new Font("SansSerif", Font.BOLD, 18);
    
    private JTextField coinsField;
    
    public CoinsPanel() {
        coinsField = new JTextField(JLabel.CENTER);
        coinsField.setFont(font);
        coinsField.setText("100");
        coinsField.setEditable(false);
        coinsField.setSize(new Dimension(250, 20));

        JLabel coinsLabel = new JLabel("Coins: ", JLabel.CENTER);
        coinsLabel.setFont(font);

        // Setting the top panel
        //setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 20));
        setLayout(new FlowLayout(FlowLayout.LEFT, SCREEN_SIZE.width / 7, 10));
        add(coinsLabel);
        add(coinsField);
    }
    
    public void increaseCoins(int value) {
       int actualValue = Integer.parseInt(coinsField.getText());
       coinsField.setText((actualValue + value) + "");
      
    }
    
    public boolean decreaseCoins(int value) {
       int actualValue = Integer.parseInt(coinsField.getText());
       if (actualValue - value > 0) {
        coinsField.setText((actualValue + value) + "");
        return true;
       } else {
        return false;
       }
    }
    
}
