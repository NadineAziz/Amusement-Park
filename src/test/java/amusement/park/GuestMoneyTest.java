/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author khaligov
 */
public class GuestMoneyTest {
    @Test
    public void test_GuestPayEntranceFee() {
        moneyManager guestMoneyManager = new moneyManager();
        guestMoneyManager.payEntranceFee();
        assertEquals(70, guestMoneyManager.getGuestMoney());
    }
    
    @Test
    public void test_GuestPayUsageFee() {
        moneyManager guestMoneyManager = new moneyManager();
        guestMoneyManager.payBuildingFee();
        assertEquals(90, guestMoneyManager.getGuestMoney());
    }
    
    @Test
    public void test_ATMwithdrawal() {
        moneyManager guestMoneyManager = new moneyManager();
        guestMoneyManager.getMoneyFromATM();
        assertEquals(200, guestMoneyManager.getGuestMoney());
    }
    
}
