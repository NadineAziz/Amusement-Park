package amusement.park.model.buildings.games;


public class SecondGame extends BaseGame {

    public SecondGame(int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super("game2.png", moodChange, valueOfTheProduct, turnsToBeReady, "SecondGame");
    }

    public SecondGame() {
        this(5, 10, 10);
        this.setTurnTime(7);
    }

    @Override
    public Object clone() {
        return new SecondGame();
    }
}