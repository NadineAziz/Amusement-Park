package amusement.park.model.buildings.games;

public class SecondGame extends BaseGame {
    public SecondGame() {
        super("games/game2.png", 5, 20, 10);
    }

        @Override
    public Object clone() {
        return new FirstGame();
    }
}