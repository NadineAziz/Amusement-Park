package amusement.park.model.buildings.games;

public class FirstGame extends BaseGame {

    public FirstGame(int moodChange, int gamePrice, int turnsToBeReady) {
        super("game1.png", moodChange, gamePrice, turnsToBeReady, "FirstGame");
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
