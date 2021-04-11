package amusement.park.model.buildings.games;


public class SecondGame extends BaseGame {

    public SecondGame(int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super("games/game2.png", moodChange, valueOfTheProduct, turnsToBeReady);
    }

    public SecondGame() {
        this(5, 10, 10);
    }

    @Override
    public Object clone() {
        return new SecondGame();
    }
}