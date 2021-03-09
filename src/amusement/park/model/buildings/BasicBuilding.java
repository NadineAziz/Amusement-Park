package amusement.park.model.buildings;

import static amusement.park.GameArea.*;
import amusement.park.model.Guest;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicBuilding implements Cloneable {
    private final String picture;
    private ImageIcon icon;
    private final int turnsToBeReady;
    private final int turnsToBeBroken = 10;
    private int auxTurnsToBeBroken = turnsToBeBroken;
    private int turnsToBeRepaired = 15;
    private int auxTurnsToBeRepaired = turnsToBeRepaired;
    private int auxTurnsToBeReady;

    public BasicBuilding(String picture, int turnsToBeReady) {
        this.picture = picture;
        this.turnsToBeReady = turnsToBeReady;
        auxTurnsToBeReady = turnsToBeReady;
        try {
            icon = new ImageIcon(Paths.get("images/" + picture).toUri().toURL());
        } catch (MalformedURLException ex) {
       
            icon = null;
        }
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
        graphics.drawImage(getIcon().getImage(),  x, y, UNIT_SIZE, UNIT_SIZE, null);
        if (auxTurnsToBeReady > 0) {
            graphics.setColor(Color.yellow);
            ((Graphics2D) graphics).setStroke(new BasicStroke(5));
            graphics.drawLine(x, y, x + (UNIT_SIZE * auxTurnsToBeReady) / turnsToBeReady, y);
            decreaseTurnsToBeReady();
        } else {
            if (auxTurnsToBeBroken > 0) {
                graphics.setColor(Color.green);
                ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                graphics.drawLine(x, y, x + (UNIT_SIZE * auxTurnsToBeBroken) / turnsToBeBroken, y);
            } else if (auxTurnsToBeRepaired > 0){
                graphics.setColor(Color.red);
                ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                graphics.drawLine(x, y, x + (UNIT_SIZE * auxTurnsToBeRepaired) / turnsToBeRepaired, y);
            }
        }
    }

    @Override
    public Object clone() {
        return new BasicBuilding(picture, turnsToBeReady);
    }
}
