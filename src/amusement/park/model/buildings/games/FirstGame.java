package amusement.park.model.buildings.games;

public class FirstGame extends BaseGame {

    public FirstGame(int moodChange, int valueOfTheProduct, int turnsToBeReady) {
        super("game1.png", moodChange, valueOfTheProduct, turnsToBeReady, "FirstGame");
        this.setTurnTime(6);
    }

    public FirstGame() {
        this(5, 10, 10);
    }

    @Override
    public Object clone() {
        return new FirstGame();
    }
}
