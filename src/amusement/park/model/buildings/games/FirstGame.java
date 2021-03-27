package amusement.park.model.buildings.games;

import amusement.park.model.buildings.Building;


public class FirstGame extends Building {
    
    public FirstGame() {
        super("games/game1.png", 5, 10, 10);
    }

    @Override
    public Object clone() {
        return new FirstGame();
    }
}
}
