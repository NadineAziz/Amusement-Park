
package amusement.park;

import amusement.park.model.Guest;
import amusement.park.model.buildings.ATM;
import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.ThiefDen;
import amusement.park.model.buildings.PoliceStation;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GameArea extends JPanel{
    public static final int UNIT_SIZE = 50;
    private static final int GAME_AREA_WIDTH = 850;
    private static final int GAME_AREA_HEIGHT = 500;

    private final BasicBuilding[][] placesMatrix;
    private final static Random random = new Random();
    private final int numberOfRows = GAME_AREA_HEIGHT / UNIT_SIZE;
    private final int numberOfCols = GAME_AREA_WIDTH / UNIT_SIZE;
    private Guest guest = new Guest(100);

    public GameArea(GamePanel gamePanel) {
        super();
        placesMatrix = new BasicBuilding[numberOfCols][numberOfRows];
        placeRandomBuildings();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (gamePanel.getSelectedItem() != null) {
                    BasicBuilding building = (BasicBuilding) gamePanel.getSelectedItem().getBuilding();
                    int indexX = (e.getX() / UNIT_SIZE);
                    int indexY = (e.getY() / UNIT_SIZE);
                    addBuilding(building, indexX, indexY);
                }
                repaint();
            }
        });

        new Timer(gamePanel.getFps(), new NewFrameListener()).start();

    }

    private boolean addBuilding(BasicBuilding building, int indexX, int indexY) {
        if (indexX < numberOfCols && indexY < numberOfRows) {
            if (placesMatrix[indexX][indexY] == null) {
                placesMatrix[indexX][indexY] = building;
                return true;
            } else  {
                return false;
            }
        } else {
            return false;
        }
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
            indexX = random.nextInt(numberOfCols - 1);
            indexY = random.nextInt(numberOfRows - 1);
        }
    }

    /**
     * Moves the guests in the matrix
     */
    public void moveGuests() {

     }

     class NewFrameListener implements ActionListener {

         @Override
         public void actionPerformed(ActionEvent e) {
             moveGuests();
             repaint();
         }
     }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
//          Display a grid
        for (int i = 0; i <= GAME_AREA_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, GAME_AREA_HEIGHT );
            g.drawLine(0, i * UNIT_SIZE, GAME_AREA_WIDTH, i * UNIT_SIZE);
        }

        guest.draw(g);
        guest.changeMood(5);

        for (int i = 0; i < numberOfCols; ++i) {
            for (int j = 0; j < numberOfRows; ++j) {
                if (placesMatrix[i][j] != null) {
                    placesMatrix[i][j].draw(g, i * UNIT_SIZE, j * UNIT_SIZE);
                }
            }
        }

    }
}
