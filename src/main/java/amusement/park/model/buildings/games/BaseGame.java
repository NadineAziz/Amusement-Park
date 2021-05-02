package amusement.park.model.buildings.games;

import amusement.park.model.Guest;
import amusement.park.model.buildings.Building;

public class BaseGame extends Building {

    public int numOfGuests = 0;
    private int capacityOfGame;

    public BaseGame(String picture, int moodChange, int gamePrice, int turnsToBeReady, String buildingType) {
        super(picture, moodChange, gamePrice, turnsToBeReady, 2, buildingType);
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
