package amusement.park.ui;

import amusement.park.model.buildings.BasicBuilding;

import javax.swing.*;

public class BuildingItem extends JButton {
        private final BasicBuilding building;

    public BuildingItem(BasicBuilding building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }

    public Object getBuilding() {
        return building.clone();
    }
}
