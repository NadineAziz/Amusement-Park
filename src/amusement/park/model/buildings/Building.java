package amusement.park.model.buildings;

import amusement.park.model.Guest;
import amusement.park.ui.BuildingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

public class Building extends BasicBuilding {
     private final int moodChange;
    private final int valueOfTheProduct;

    public Building(String picture,
                    int moodChange,
                    int valueOfTheProduct,
                    int turnsToBeReady) {
        super(picture, turnsToBeReady);
        this.moodChange = moodChange;
        this.valueOfTheProduct = valueOfTheProduct;
    }

    public void performAction(Guest guest) {
        guest.pay(valueOfTheProduct);
        guest.changeMood(moodChange);
    }

    @Override
    public Object clone() {
        return new Building(getPicture(), moodChange, valueOfTheProduct, getTurnsToBeReady());
    }
}
