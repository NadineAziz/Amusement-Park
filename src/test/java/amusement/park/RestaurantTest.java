/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

import amusement.park.model.buildings.restaurants.Restaurant;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author PC
 */
public class RestaurantTest {
    @Test
    public void test_getPrice(){
        Restaurant r = new Restaurant("",0,0,0,"");
        assertEquals(r.getPrice(), 5);
    }

}
