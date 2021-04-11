
package amusement.park;

import amusement.park.model.Guest;
import amusement.park.model.buildings.ATM;
import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.PoliceStation;
import amusement.park.model.buildings.ThiefDen;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.model.buildings.games.FirstGame;
import amusement.park.model.buildings.games.SecondGame;
import amusement.park.model.buildings.games.ThirdGame;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameArea extends JPanel{
    public static final int UNIT_SIZE = 50;
    private static final int GAME_AREA_WIDTH = 850;
    private static final int GAME_AREA_HEIGHT = 450;

    private final BasicBuilding[][] placesMatrix;
    private final static Random random = new Random();
    private final int numberOfRows = GAME_AREA_HEIGHT / UNIT_SIZE;
    private final int numberOfCols = GAME_AREA_WIDTH / UNIT_SIZE;
    //private Guest guest = new Guest(100);
    private final List<Guest> guests;

    public GameArea(GamePanel gamePanel) {
        super();
        this.guests = gamePanel.getGuests();
        placesMatrix = new BasicBuilding[numberOfRows][numberOfCols];
        placeRandomBuildings();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gamePanel.getSelectedItem() != null) {
                    BasicBuilding building = (BasicBuilding) gamePanel.getSelectedItem().getBuilding();
                    int indexX = (e.getX() / UNIT_SIZE);
                    int indexY = (e.getY() / UNIT_SIZE);
                    addBuilding(building, indexY, indexX);
                    repaint();
                } else {
                    System.out.println("null");
                }
            }
        });

        new Timer(gamePanel.getFps(), new NewFrameListener()).start();

    }

    private boolean addBuilding(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            if (indexX < numberOfRows && indexY < numberOfCols) {
                if (placesMatrix[indexX][indexY] == null) {
                    placesMatrix[indexX][indexY] = building;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
        private boolean checkIfGameExists(BasicBuilding building) {
        if (building instanceof BaseGame) {
            System.out.println(building.getClass());
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


    public BasicBuilding getBuilding(int posX, int posY) {
        return placesMatrix[posX][posY];
    }

    /**
     * Displays the police station, cave and atm to random empty places
     *
     */
    private void placeRandomBuildings() {
        BasicBuilding policeStation = new PoliceStation();
        BasicBuilding cave = new ThiefDen();
        BasicBuilding atm = new ATM();
        int indexX = 0;
        int indexY = 0;
        addBuilding(policeStation, indexX, indexY);

        tryPlacingBuilding(cave, indexX, indexY);
        tryPlacingBuilding(atm, indexX, indexY);
        
    }

    private void tryPlacingBuilding(BasicBuilding building, int indexX, int indexY) {
        while (!addBuilding(building, indexX, indexY)) {
            indexX = random.nextInt(numberOfRows - 1);
            indexY = random.nextInt(numberOfCols - 1);
        }
    }

    /**
     * Moves the guests in the matrix
     */
    public void moveAllGuests() {
        this.guests.forEach(guest -> {
            guest.changeDirection();
        });
     }

     class NewFrameListener implements ActionListener {

         @Override
         public void actionPerformed(ActionEvent e) {
             moveAllGuests();
             repaint();
         }
     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        this.guests.forEach(guest -> {
            guest.draw(g);
        });
        //guest.draw(g);
        //guest.changeMood(5);
//          Display a grid
        for (int i = 0; i <= GAME_AREA_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, GAME_AREA_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, GAME_AREA_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    placesMatrix[i][j].draw(g, j * UNIT_SIZE, i * UNIT_SIZE);
                }
            }
        }

    }
}
