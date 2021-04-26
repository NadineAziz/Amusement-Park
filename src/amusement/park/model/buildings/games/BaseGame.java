package amusement.park.model.buildings.games;

import amusement.park.model.Guest;
import amusement.park.model.buildings.Building;


public class BaseGame extends Building {
    private int capacityOfGame;
    public int numOfGuests = 0;

    public BaseGame(String picture, int moodChange, int valueOfTheProduct, int turnsToBeReady, String buildingType) {
        super(picture, moodChange, valueOfTheProduct, turnsToBeReady, 2, buildingType);
        this.setTurnTime(5);
    }



    public void standInQueue(Guest guest) {

    }

    public void acceptGuests(Guest guest) {
        if (numOfGuests < capacityOfGame) {
            numOfGuests++;
        } else {
            standInQueue(guest);
        }
    }
    


}
