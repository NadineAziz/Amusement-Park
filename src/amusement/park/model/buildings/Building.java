package amusement.park.model.buildings;

import amusement.park.model.Guest;

import java.util.HashMap;
import java.util.Map;

public class Building extends BasicBuilding {
    private final int moodChange;
    private final int valueOfTheProduct;
    private static final Map<Class<? extends Building>, Integer> prices = new HashMap<>();

    public Building(String picture,
                    int moodChange,
                    int valueOfTheProduct,
                    int turnsToBeReady, int size, String buildingType) {
        super(picture, turnsToBeReady, size, buildingType);
        this.moodChange = moodChange;
        this.valueOfTheProduct = valueOfTheProduct;
    }


    public Building(String picture,
                    int moodChange,
                    int valueOfTheProduct,
                    int turnsToBeReady, String buildingType) {
        super(picture, turnsToBeReady, buildingType);
        this.moodChange = moodChange;
        this.valueOfTheProduct = valueOfTheProduct;
    }

    public void setBuildingPrice(int price) {
        prices.putIfAbsent(getClass(), price);
    }

    public int getBuildingPrice() {
        return prices.get(getClass());
    }

    public boolean hasPrice() {
        return prices.containsKey(getClass());
    }

    public void performAction(Guest guest) {
        guest.pay(valueOfTheProduct);
        guest.changeMood(moodChange);
    }

    @Override
    public Object clone() {
        return new Building(getPicture(), moodChange, valueOfTheProduct, getTurnsToBeReady(), getSize(), getBuildingType());
    }
}
