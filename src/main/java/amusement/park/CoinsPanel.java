package amusement.park;

import javax.swing.*;
import java.awt.*;

import static amusement.park.GameGUI.SCREEN_SIZE;

public class CoinsPanel extends JPanel implements CoinsManager {

    private static final Font font = new Font("SansSerif", Font.BOLD, 18);
    private JTextField coinsField;
    private final CoinsManager coinsManager;

    public CoinsPanel() {
        this.coinsManager = new CoinsManagerImpl(); 
        coinsField = new JTextField(JLabel.CENTER);
        coinsField.setFont(font);
        coinsField.setText(String.valueOf(coinsManager.getValue()));
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

    @Override
    public boolean increaseCoins(int value) {
        boolean ret = coinsManager.increaseCoins(value);
        coinsField.setText(coinsManager.getValue() + "");
        return ret;

    }

    @Override
    public boolean hasEnoughMoney(int value) {
        return coinsManager.hasEnoughMoney(value);
    }

    @Override
    public boolean decreaseCoins(int value) {
        boolean canDecrease = coinsManager.decreaseCoins(value);
        if (canDecrease) {
            coinsField.setText(coinsManager.getValue() + "");
            return canDecrease;
        } else {
            return canDecrease;
        }
    }

    @Override
    public Integer getValue() {
        return this.coinsManager.getValue();
    }
}
