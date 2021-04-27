/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model;

/**
 *
 * @author User
 */
public class Security extends Person {

    public Security() {
        super("security.png");
        this.setY(0);
    }

    public void seccuritygoestosb() {
        this.destination = "SecurityBuilding";
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String getDestination() {
        return this.destination;
    }
}
