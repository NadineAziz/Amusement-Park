package amusement.park;

import amusement.park.model.buildings.ATM;
import amusement.park.model.buildings.PoliceStation;
import amusement.park.model.buildings.ThiefDen;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.model.buildings.games.FirstGame;
import amusement.park.model.buildings.games.SecondGame;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlaceManagerTest {

    @Test
    public void test_addingBuildingToAFreeSpace() {
        PlaceManager placeManager = new PlaceManager(5, 5);
        assertTrue(placeManager.addBuilding(new PoliceStation(), 0, 0));
    }

    @Test
    public void test_addingTheSameGame2Times() {
        PlaceManager placeManager = new PlaceManager(5, 5);
        BaseGame game1 = new FirstGame();
        BaseGame game2 = new FirstGame();
        BaseGame game3 = new SecondGame();
        assertFalse(placeManager.checkIfGameExists(game1));
        assertTrue(placeManager.addBuilding(game1, 0, 0));
        assertFalse(placeManager.addBuilding(game2, 2, 2));
        assertTrue(placeManager.checkIfGameExists(game2));
        assertFalse(placeManager.checkIfGameExists(game3));
    }

    @Test
    public void test_canBePlaced() {
        PlaceManager placeManager = new PlaceManager(5, 5);
        assertTrue(placeManager.canBePlaced(0, 0));
        placeManager.addBuilding(new ThiefDen(),0, 0 );
        assertFalse(placeManager.canBePlaced(0, 0));
    }

    @Test
    public void test_getCorrectPlace() {
        PlaceManager placeManager = new PlaceManager(5, 5);
        assertTrue(placeManager.addBuilding(new ThiefDen(),0, 0));
        assertTrue(placeManager.addBuilding(new ATM(), 2, 2));
    }

}
