package amusement.park.model.buildings;

import amusement.park.model.Guest;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;

import static amusement.park.GameArea.UNIT_SIZE;

public class BasicBuilding implements Cloneable {
    private final String picture;
    private final int turnsToBeReady;
    private final int turnsToBeBroken = 10;
    private ImageIcon icon;
    private int auxTurnsToBeBroken = turnsToBeBroken;
    private int turnsToBeRepaired = 15;
    private int auxTurnsToBeRepaired = turnsToBeRepaired;
    private int auxTurnsToBeReady;
    private final int size;
    private String buildingType;

    public BasicBuilding(String picture, int turnsToBeReady,String buildingType) {
        this(picture, turnsToBeReady, 1, buildingType);
    }

    public BasicBuilding(String picture, int turnsToBeReady, int size, String buildingType) {
        this.picture = picture;
        this.size = size;
        this.turnsToBeReady = turnsToBeReady;
        this.buildingType = buildingType;
        auxTurnsToBeReady = turnsToBeReady;
        try {
            icon = new ImageIcon(Paths.get("images/" + picture).toUri().toURL());
        } catch (MalformedURLException ex) {

            icon = null;
        }
    }
    
    public String getBuildingType(){
        return buildingType;
    }

    public int getSize() {
        return size;
    }
    
    

    public int getTurnsToBeReady() {
        return turnsToBeReady;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getPicture() {
        return picture;
    }


    public synchronized void decreaseTurnsToBeReady() {
        --auxTurnsToBeReady;
    }

    /**
     * This method is going to increase the mood of the guests
     * and decrease their money
     *
     * @param guest
     */
    public void host(Guest guest) {

    }


    public void draw(Graphics graphics, int x, int y) {
        int unitSize = UNIT_SIZE * getSize();
        graphics.drawImage(getIcon().getImage(), x, y, unitSize, unitSize, null);

        if (turnsToBeReady > 0) {
            if (auxTurnsToBeReady > 0) {
                graphics.setColor(Color.yellow);
                ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                graphics.drawLine(x, y, x + (unitSize* auxTurnsToBeReady) / turnsToBeReady, y);
                decreaseTurnsToBeReady();
            } else {
                if (auxTurnsToBeBroken > 0) {
                    graphics.setColor(Color.green);
                    ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                    graphics.drawLine(x, y, x + (unitSize * auxTurnsToBeBroken) / turnsToBeBroken, y);
                } else if (auxTurnsToBeRepaired > 0) {
                    graphics.setColor(Color.red);
                    ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                    graphics.drawLine(x, y, x + (unitSize * auxTurnsToBeRepaired) / turnsToBeRepaired, y);
                }
            }
        }
    }

    @Override
    public Object clone() {
        return new BasicBuilding(picture, turnsToBeReady, buildingType);
    }
}
