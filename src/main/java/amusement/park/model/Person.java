package amusement.park.model;

import amusement.park.pathfinding.Node;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static amusement.park.GameArea.UNIT_SIZE;

public class Person {

    private final static Random random = new Random();
    public List<Node> currentPath;
    public String destination;
    public boolean reachedDestination = false;
    public boolean smth = false;
    public String pictureName;
    Node current;
    private ImageIcon icon;
    private int x = 0;
    private int y = 100;
    private int a = 0;
    private int b = 200;

    public Person(String pictureName) {
        currentPath = new ArrayList<Node>();
        try {
            icon = new ImageIcon(Paths.get("images/person/" + pictureName).toUri().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.INFO, "AAAAA", ex);
        }
    }

    public void getPosition() {
        if (!currentPath.isEmpty()) {
            this.current = currentPath.remove(0);
            if (this.current != null) {
                this.moveToIndex();
            }
        }
    }

    public void moveToIndex() {
        if (this.current != null) {
            int nextRow = this.current.getX();
            int nextCol = this.current.getY();
            int row = y / 50;
            int col = x / 50;
            if (nextRow == row + 1) {
                this.move(0, 50);
            }
            if (nextRow == row - 1) {
                this.move(0, -50);
            }
            if (nextCol == col + 1) {
                this.move(50, 0);
            }
            if (nextCol == col - 1) {
                this.move(-50, 0);
            }
        }
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void move2(int dx, int dy) {
        a += dx;
        b += dy;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.y = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int x) {
        this.x = x;
    }

    public int getA() {
        return this.a;
    }

    public int getB() {
        return this.b;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        //g.drawLine(x, y, (UNIT_SIZE * mood) / 100, y);
        g.drawImage(getIcon().getImage(), x, y, UNIT_SIZE, UNIT_SIZE - 15, null);
    }

    public String getDestination() {
        return null;
    }

    public void setDestination(String destination) {

    }
}
