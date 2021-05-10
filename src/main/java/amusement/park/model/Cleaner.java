/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model;

import amusement.park.model.buildings.Path;
import amusement.park.model.buildings.gardens.Trash;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
import amusement.park.pathfinding.PathFinder;

/**
 * @author PC
 */
public class Cleaner extends Person {

    //int startx;
    //int starty;
    //int radius;
    private Random random;
    private final int cost=20;
    public int getCost() {
        return cost;
    }
    
    public Cleaner() {
        super("person/cleaner.gif");
        Random rand= new Random();
    }

    public void pickUpTrash() {
        if(!currentPath.isEmpty()) {
            int r=random.nextInt(currentPath.size());
            //Point one=grid.getPoint(getImg().getLocation)
        }
    }

    public void findTrash(Path path) {

    }
    
    public void leaveThePark() {
        this.setDestination("PoliceStation");
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }
}
