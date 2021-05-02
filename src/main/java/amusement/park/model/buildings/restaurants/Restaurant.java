package amusement.park.model.buildings.restaurants;

import amusement.park.model.buildings.Building;

public class Restaurant extends Building {

    private final int price = 5;
    private final int capacity = 10;

    public Restaurant(String picture, int moodChange, int restaurantPrice, int turnsToBeReady, String buildingType) {
        super(picture, moodChange, restaurantPrice, turnsToBeReady, 2, buildingType);
        this.setTurnTime(5);
    }

    public int getPrice() {
        return price;
    }
}
