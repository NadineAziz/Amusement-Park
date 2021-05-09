package amusement.park;


import org.junit.Test;

import static org.junit.Assert.*;

public class CoinsManagerTest {

    @Test
    public void test_increasingMoney() {
        CoinsManager coinsManager = new CoinsManagerImpl();
        
        assertTrue(coinsManager.increaseCoins(100));
        assertEquals(200, (int) coinsManager.getValue());
    }

    @Test
    public void test_decreasingMoney() {
        CoinsManager coinsManager = new CoinsManagerImpl();

        assertTrue(coinsManager.decreaseCoins(20));
        assertEquals(80, (int) coinsManager.getValue());
    }

}