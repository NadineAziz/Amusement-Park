package amusement.park.model.buildings.games;

public class ThirdGame extends BaseGame {
     public ThirdGame() {
        super("games/game3.png", 5, 25, 10);
    }

    @Override
    public Object clone() {
        return new ThirdGame();
    }
}