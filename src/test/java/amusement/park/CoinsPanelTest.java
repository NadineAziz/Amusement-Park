package amusement.park;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinsPanelTest {

    @Test
    public void test_increasingMoney() {
        CoinsPanel coinsPanel = new CoinsPanel();
        coinsPanel.increaseCoins(100);

        assertEquals(200, (int) coinsPanel.getValue());
    }

    @Test
    public void test_decreasingMoney() {
        CoinsPanel coinsPanel = new CoinsPanel();
        coinsPanel.decreaseCoins(20);

        assertEquals(80, (int) coinsPanel.getValue());
    }

}