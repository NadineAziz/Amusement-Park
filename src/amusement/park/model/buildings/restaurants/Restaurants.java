/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model.buildings.restaurants;
import amusement.park.model.buildings.Building;


/**
 *
 * @author kristina
 */
public class Restaurants extends Building{
    private final int price=5;
    private final int capacity=10;

    public Restaurants(String picture, int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super(picture, moodChange, valueOfTheProduct, turnsToBeReady);
    }

    
    public int getPrice() {
        return price;
    }
}
