package amusement.park;

import javax.swing.*;
import java.awt.*;

import static amusement.park.GameGUI.SCREEN_SIZE;

public class CoinsPanel extends JPanel {

    private static final Font font = new Font("SansSerif", Font.BOLD, 18);
    public static int moneyy = 100;
    private JTextField coinsField;

    public CoinsPanel() {
        coinsField = new JTextField(JLabel.CENTER);
        coinsField.setFont(font);
        coinsField.setText(String.valueOf(moneyy));
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

    public JTextField getCoinsField() {
        return coinsField;
    }

    public void setCoinsField(JTextField coinsField) {
        this.coinsField = coinsField;
    }

    public boolean increaseCoins(int value) {
        int actualValue = Integer.parseInt(coinsField.getText());
        coinsField.setText((actualValue + value) + "");
        return true;

    }

    public boolean hasEnoughMoney(int value) {
        int actualValue = Integer.parseInt(coinsField.getText());
        return (actualValue - value) >= 0;
    }

    public boolean decreaseCoins(int value) {
        int actualValue = Integer.parseInt(coinsField.getText());
        if (hasEnoughMoney(value)) {
            coinsField.setText((actualValue - value) + "");
            return true;
        } else {
            return false;
        }
    }

    public Integer getValue() {
        return Integer.parseInt(coinsField.getText());
    }
}
