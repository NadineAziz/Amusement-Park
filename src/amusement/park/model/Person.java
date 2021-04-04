package amusement.park.model;

import static amusement.park.GameArea.UNIT_SIZE;
import amusement.park.model.buildings.BasicBuilding;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Person {
    private ImageIcon icon;
    private int x = 0;
    private int y = 0;
    BasicBuilding[][] placesMatrix;
    private final static Random random = new Random();

    public Person(String pictureName) {
        try {
            icon = new ImageIcon(Paths.get("images/person/" + pictureName).toUri().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.INFO, "AAAAA", ex);
        }
    }
    
    enum Direction {
        DOWN, UP, LEFT, RIGHT;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    
    private void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void changeDirection() {
        //Guest.Direction.values()[random.nextInt(4)]
        switch (Guest.Direction.values()[random.nextInt(4)]) {
            case UP:
                move(50, 0);     
                break;
            case DOWN:
                move(-50, 0);
                break;
            case LEFT:
                move(0, 50);
                break;
            case RIGHT:
                move(0, -50);
                break;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        //g.drawLine(x, y, (UNIT_SIZE * mood) / 100, y);
        g.drawImage(getIcon().getImage(), x, y, UNIT_SIZE, UNIT_SIZE - 15, null);
    }
}
