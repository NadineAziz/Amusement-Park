package amusement.park.model.buildings;

import amusement.park.model.Guest;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Arrays;

import static amusement.park.GameArea.UNIT_SIZE;

enum State {
    BEING_BUILT, IN_USE, NEEDS_TO_BE_REPAIRED
}

public class BasicBuilding implements Cloneable {

    private final String picture;
    private final int turnsToBeReady;
    private final int turnsToBeBroken = 10;
    private final int size;
    private final String[] Destinations = {"FirstGame", "SecondGame", "ThirdGame", "SweetShop", "Buffet", "HotDogStand"};
    public long startTimer;
    public int turnTime = 3;
    private State state;
    private ImageIcon icon;
    private int auxTurnsToBeBroken = turnsToBeBroken;
    private int turnsToBeRepaired = 15;
    private int auxTurnsToBeRepaired = turnsToBeRepaired;
    private int auxTurnsToBeReady;
    private String buildingType;
    private int x, y;

    public BasicBuilding(String picture, int turnsToBeReady, String buildingType) {
        this(picture, turnsToBeReady, 1, buildingType);
        state = state.IN_USE;
    }

    public BasicBuilding(String picture, int turnsToBeReady, int size, String buildingType) {
        this.picture = picture;
        this.size = size;
        this.turnsToBeReady = turnsToBeReady;
        this.buildingType = buildingType;
        auxTurnsToBeReady = turnsToBeReady;
        state = state.IN_USE;
        try {
            icon = new ImageIcon(Paths.get("images/" + picture).toUri().toURL());
        } catch (MalformedURLException ex) {

            icon = null;
        }
    }

    public void breakDownTheGame() {

        if (Arrays.asList(Destinations).contains(this.getBuildingType())) {
            this.state = state.NEEDS_TO_BE_REPAIRED;
            this.setBuildingType(this.getBuildingType());
            try {
                this.icon = new ImageIcon(Paths.get("images/" + "fix.png").toUri().toURL());
            } catch (MalformedURLException ex) {

                this.icon = null;
            }

        }
    }

    public boolean isWorking() {
        return !this.state.equals(state.NEEDS_TO_BE_REPAIRED);
    }

    public void setPrevPic() {
        try {
            this.icon = new ImageIcon(Paths.get("images/" + this.picture).toUri().toURL());
        } catch (MalformedURLException ex) {

            this.icon = null;
        }

        this.setState(state.IN_USE);
    }

    public State getState() {
        return this.state;
    }

    public void setState(State s) {
        this.state = s;
    }

    public int getTurnTime() {
        return this.turnTime;
    }

    public void setTurnTime(int time) {
        this.turnTime = time;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String s) {
        this.buildingType = s;
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
     * This method is going to increase the mood of the guests and decrease
     * their money
     *
     * @param guest
     */
    public void host(Guest guest) {

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    /**
     * Drawing building UI to graphics
     * @param graphics game panel UI
     * @param x position of the building
     * @param y position of the building
     */
    public void draw(Graphics graphics, int x, int y) {
        this.x = x;
        this.y = y;
        int unitSize = UNIT_SIZE * getSize();
        graphics.drawImage(getIcon().getImage(), x, y, unitSize, unitSize, null);

        if (turnsToBeReady > 0) {
            if (auxTurnsToBeReady > 0) {
                graphics.setColor(Color.yellow);
                ((Graphics2D) graphics).setStroke(new BasicStroke(5));
                graphics.drawLine(x, y, x + (unitSize * auxTurnsToBeReady) / turnsToBeReady, y);
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
