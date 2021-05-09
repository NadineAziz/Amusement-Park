package amusement.park.ui;

import amusement.park.model.buildings.Building;
import amusement.park.model.buildings.games.BaseGame;

import javax.swing.*;

public class BuildingItem extends JButton {

    private final Building building;

    public BuildingItem(Building building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }

    public BuildingItem(BaseGame building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }

    public Building getBuilding() {
        return building;
    }

    public Building createBuilding() {
        return (Building) building.clone();
    }
}
