package amusement.park.model.buildings.games;

public class ThirdGame extends BaseGame {

    public ThirdGame(int moodChange, int gamePrice, int turnsToBeReady) {
        super("game3.png", moodChange, gamePrice, turnsToBeReady, "ThirdGame");
    }

    public ThirdGame() {
        this(5, 15, 10);
        this.setTurnTime(8);
    }

    @Override
    public Object clone() {
        return new ThirdGame();
    }
}
