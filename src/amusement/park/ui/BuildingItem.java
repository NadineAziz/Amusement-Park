package amusement.park.ui;

import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.model.buildings.gardens.Garden;

import javax.swing.*;

public class BuildingItem extends JButton {
        private final BasicBuilding building;

    public BuildingItem(BasicBuilding building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }
    
    public BuildingItem(Garden building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }
    
    public BuildingItem(BaseGame building) {
        this.building = building;
        setIcon(building.getIcon());
        this.setBackground(null);
    }

    public Object getBuilding() {
        return building.clone();
    }
}
