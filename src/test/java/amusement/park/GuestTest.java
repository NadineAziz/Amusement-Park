/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author khaligov
 */
public class GuestTest {
    @Test
    public void test_checkParkOpen() {
        PlaceManagerGuests guestManager = new PlaceManagerGuests();
        guestManager.openPark();
        assertTrue(guestManager.checkPark());
    }
    
    @Test
    public void test_acceptGuestsWhileParkClosed() {
        PlaceManagerGuests guestManager = new PlaceManagerGuests();
        guestManager.setGuests(5);
        assertFalse(guestManager.parkHasGuests());
    }
    
    @Test
    public void test_acceptGuestsWhileParkOpen() {
        PlaceManagerGuests guestManager = new PlaceManagerGuests();
        guestManager.openPark();
        guestManager.setGuests(5);
        assertTrue(guestManager.parkHasGuests());
        assertEquals(5, guestManager.getNumOfQuests());
    }
    
}
