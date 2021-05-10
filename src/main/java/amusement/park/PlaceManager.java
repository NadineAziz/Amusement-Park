package amusement.park;

import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.model.buildings.games.FirstGame;
import amusement.park.model.buildings.games.SecondGame;
import amusement.park.model.buildings.games.ThirdGame;
import amusement.park.model.buildings.gardens.Trash;

public class PlaceManager {

    private final int numberOfRows;
    private final int numberOfCols;
    private final BasicBuilding[][] placesMatrix;

    public PlaceManager(int numberOfRows, int numberOfCols) {
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;

        placesMatrix = new BasicBuilding[numberOfRows][numberOfCols];
    }

    public BasicBuilding[][] getPlaces() {
        return placesMatrix;
    }

    public boolean addBuilding(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            System.out.println(building instanceof Trash);
            boolean t= building instanceof Trash;
            if (canBePlaced(indexX, indexY/*, t*/) && isEnoughSpace(building, indexX, indexY)) {
                for (int x = indexX; x < (indexX + building.getSize()); x++) {
                    for (int y = indexY; y < (indexY + building.getSize()); y++) {
                        placesMatrix[x][y] = building;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean canBePlaced(int indexX, int indexY/*, boolean isTrash*/) {
        if (/*isTrash ||*/ (indexX < numberOfRows && indexY < numberOfCols)) {
            return placesMatrix[indexX][indexY] == null;
        } else {
            return false;
        }
    }

    public boolean isEnoughSpaceForCave(BasicBuilding building, int startX, int startY) {
        if ((startX + building.getSize() <= numberOfRows) && (startY + building.getSize() <= numberOfCols)) {
            for (int x = startX; x < (startX + building.getSize()); x++) {
                for (int y = startY; y < (startY + building.getSize()); y++) {
                    if (!canCaveBePlaced(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addCave(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            if (canCaveBePlaced(indexX, indexY) && isEnoughSpaceForCave(building, indexX, indexY)) {
                for (int x = indexX; x < (indexX + building.getSize()); x++) {
                    for (int y = indexY; y < (indexY + building.getSize()); y++) {
                        placesMatrix[x][y] = building;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public boolean addTrash(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            if (canCaveBePlaced(indexX, indexY) && isEnoughSpaceForCave(building, indexX, indexY)) {
                for (int x = indexX; x < (indexX + building.getSize()); x++) {
                    for (int y = indexY; y < (indexY + building.getSize()); y++) {
                        placesMatrix[x][y] = building;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean buildingExists(String buildingType) {
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    if (placesMatrix[i][j].getBuildingType().equals(buildingType) && placesMatrix[i][j].isWorking()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canCaveBePlaced(int indexX, int indexY) {
        if (indexX < numberOfRows && indexY < numberOfCols) {
            return placesMatrix[indexX][indexY].getBuildingType().equals("Path");
        } else {
            return false;
        }
    }

    public BasicBuilding getPlace(int row, int col) {
        return placesMatrix[row][col];
    }

    public boolean checkIfGameExists(BasicBuilding building) {
        if (building instanceof BaseGame) {
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfCols; j++) {
                    if (building instanceof FirstGame && placesMatrix[i][j] instanceof FirstGame) {
                        return true;
                    }

                    if (building instanceof SecondGame && placesMatrix[i][j] instanceof SecondGame) {
                        return true;
                    }

                    if (building instanceof ThirdGame && placesMatrix[i][j] instanceof ThirdGame) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isEnoughSpace(BasicBuilding building, int startX, int startY) {
        if ((startX + building.getSize() <= numberOfRows) && (startY + building.getSize() <= numberOfCols)) {
            for (int x = startX; x < (startX + building.getSize()); x++) {
                for (int y = startY; y < (startY + building.getSize()); y++) {
                    boolean t= building instanceof Trash;
                    if (!canBePlaced(x, y /*, t*/)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


}
