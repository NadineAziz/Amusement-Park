package amusement.park.model.buildings.games;

import amusement.park.model.Guest;
import amusement.park.model.buildings.Building;


public class BaseGame extends Building {
    private int turnTime;
    private int capacityOfGame;
    private int numOfGuests = 0;
    private State state;

    public BaseGame(String picture, int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super(picture, moodChange, valueOfTheProduct, turnsToBeReady);
    }

    public void setCapacity(int capacity) {
        this.capacityOfGame = capacity;
    }

    public void setTurnTime(int time) {
        this.turnTime = time;
    }

    public void setState(State s) {
        this.state = s;
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


    enum State {BEING_BUILT, IN_USE, NEEDS_TO_BE_REPAIRED}

}
