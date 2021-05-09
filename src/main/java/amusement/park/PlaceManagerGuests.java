/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

/**
 *
 * @author khaligov
 */
public class PlaceManagerGuests{
    private int numberOfQuests = 0;
    private boolean parkOpen = false;
    public PlaceManagerGuests(){

    }
    
    public void openPark(){
        this.parkOpen = true;
    }
    
    public void setGuests(int n){
        if(this.parkOpen){
            this.numberOfQuests = n;
        }
    }
    
    public boolean checkPark(){
        return this.parkOpen;
    }
    
    public boolean parkHasGuests(){
        return this.numberOfQuests>0;
    }
    
    public int getNumOfQuests(){
        return this.numberOfQuests;
    }
    
    
    
    
    
    
    
}
