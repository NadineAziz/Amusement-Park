package amusement.park.model.buildings;

import amusement.park.model.Guest;
import amusement.park.model.buildings.gardens.*;

import java.util.HashMap;
import java.util.Map;

public class Building extends BasicBuilding {

    private static final Map<Class<? extends BasicBuilding>, Integer> productPrices = new HashMap<>();
    
    static {
        productPrices.put(Path.class, 0);
        productPrices.put(Grass.class, 0);
        productPrices.put(Shrub.class, 0);
        productPrices.put(Trash.class, 0);
        productPrices.put(TrashCan.class, 0);
        productPrices.put(Tree.class, 0);
        productPrices.put(PoliceStation.class, 0);
        productPrices.put(ATM.class, 0);
    }
    
    private final int moodChange;
    private final int buildingPrice;

    public Building(String picture,
                    int moodChange,
                    int buildingPrice,
                    int turnsToBeReady,
                    int size,
                    String buildingType
    ) {
        super(picture, turnsToBeReady, size, buildingType);
        this.moodChange = moodChange;
        this.buildingPrice = buildingPrice;
    }

    public Building(String picture,
                    int moodChange,
                    int buildingPrice,
                    int turnsToBeReady,
                    String buildingType) {
        super(picture, turnsToBeReady, buildingType);
        this.moodChange = moodChange;
        this.buildingPrice = buildingPrice;
    }

    public int getBuildingPrice() {
        return this.buildingPrice;
    }

    public int getProductPrice() {
        System.out.println("get product price" + getClass() + " " + productPrices.getOrDefault(getClass(), -1));
        return productPrices.getOrDefault(getClass(), 0);
    }

    public void setProductPrice(int price) {
        productPrices.putIfAbsent(getClass(), price);
    }

    public boolean hasProductPrice() {
        return productPrices.containsKey(getClass());
    }

    public void performAction(Guest guest) {
        try {
            guest.pay(getProductPrice());
            guest.changeMood(moodChange);
        } catch (Guest.NotEnoughMoneyException ignored)  {

        }
    }

    @Override
    public Object clone() {
        return new Building(getPicture(), moodChange, buildingPrice, getTurnsToBeReady(), getSize(), getBuildingType());
    }
}
