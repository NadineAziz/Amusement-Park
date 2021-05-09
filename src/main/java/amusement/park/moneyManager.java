/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

import amusement.park.model.Guest;

/**
 *
 * @author khaligov
 */
public class moneyManager {
    Guest guest = new Guest(100);
    public moneyManager(){
    }
    
    public void payEntranceFee() {
        try {
            guest.pay(30);
            System.out.println(guest.getMoney());
        } catch (Guest.NotEnoughMoneyException e) {
            System.out.println("Not enough money");
        }
    }
    
    public void payBuildingFee(){
        guest.generateDestination();
        if (guest.destination != "")
        {
            try {
                guest.pay(10);
            } catch (Guest.NotEnoughMoneyException e) {
                System.out.println("Not enough money");
            }
        }
    }
    
    public void getMoneyFromATM(){
        guest.fillPocket(200);
    }
    
    public int getGuestMoney(){
        return guest.getMoney();
    }
    
    
}
