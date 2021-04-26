/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model;


/**
 *
 * @author khaligov
 */
public class Repairman extends Person {

    public boolean isCalled = false;
    private String destination;

    public Repairman() {
        super("repairman.png");
        this.reachedDestination = false;
    }

    public void fixTheGame(String gameType) {
        this.setDestination(gameType);
    }

    public void leaveThePark() {
        this.setDestination("PoliceStation");
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }
}
