package amusement.park.model.buildings.games;


public class ThirdGame extends BaseGame {

    public ThirdGame(int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super("game3.png", moodChange, valueOfTheProduct, turnsToBeReady, "ThirdGame");
    }

    public ThirdGame() {
        this(5, 10, 10);
        this.setTurnTime(8);
    }

    @Override
    public Object clone() {
        return new ThirdGame();
    }
}