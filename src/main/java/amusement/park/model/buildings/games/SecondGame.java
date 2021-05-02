package amusement.park.model.buildings.games;

public class SecondGame extends BaseGame {

    public SecondGame(int moodChange, int gamePrice, int turnsToBeReady) {
        super("game2.png", moodChange, gamePrice, turnsToBeReady, "SecondGame");
    }

    public SecondGame() {
        this(5, 12, 10);
        this.setTurnTime(7);
    }

    @Override
    public Object clone() {
        return new SecondGame();
    }
}
