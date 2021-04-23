package amusement.park.model.buildings.restaurants;
import amusement.park.model.buildings.Building;


public class Restaurants extends Building {
    private final int price=5;
    private final int capacity=10;

    public Restaurants(String picture, int moodChange, int valueOfTheProduct, int turnsToBeReady, String buildingType) {
        super(picture, moodChange, valueOfTheProduct, turnsToBeReady, 2, buildingType);
        this.setTurnTime(5);
    }

    
    public int getPrice() {
        return price;
    }
}