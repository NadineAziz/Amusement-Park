/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model.buildings.games;
import amusement.park.model.Guest;
import amusement.park.model.buildings.Building;
/**
 *
 * @author khaligov
 */
public class BaseGame extends Building{
    protected int turnTime;
    protected int capacityOfGame;
    protected int numOfGuests = 0;
    enum State { BEING_BUILT,IN_USE,NEEDS_TO_BE_REPAIRED };
    State state;
    
    public BaseGame(String picture,int moodChange,int valueOfTheProduct,int turnsToBeReady){
        super(picture,moodChange,valueOfTheProduct,turnsToBeReady);
    }
    
    public void setCapacity(int capacity){
        this.capacityOfGame = capacity;
    }
    
    public void setTurnTime(int time){
        this.turnTime = time;
    }
    
    public void setState(State s){
        this.state = s;
    }
    
    public void standInQueue(Guest guest){
        
    }
    

    public void acceptGuests(Guest guest){
        if (numOfGuests<capacityOfGame) {
            numOfGuests++;
        }
        else{
            standInQueue(guest);
        }
    }
    
    
}
