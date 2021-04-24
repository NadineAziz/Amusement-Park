package amusement.park.model;

import static amusement.park.GameArea.UNIT_SIZE;
import amusement.park.model.buildings.BasicBuilding;
import amusement.park.pathfinding.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Person {
    private ImageIcon icon;
    private int x = 0;
    private int y = 100;
    public List<Node> currentPath;
    Node current;
    public String destination;
    public boolean reachedDestination = false;
    public boolean smth=false;
    private int a = 0;
    private int b = 200;
    private final static Random random = new Random();
    public String pictureName;

    public Person(String pictureName) {
        currentPath = new ArrayList<Node>();
        try {
            icon = new ImageIcon(Paths.get("images/person/" + pictureName).toUri().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.INFO, "AAAAA", ex);
        }
    }
    
    public void getPosition(){
        if(!currentPath.isEmpty()){
            this.current = currentPath.remove(0);
            //System.out.println("Path: "+ current.getX() + " "+ current.getY());
            if(this.current != null){
                this.moveToIndex();
            }
        }
    }
    
    public void moveToIndex(){
        if(this.current != null){
            int nextRow = this.current.getX();
            int nextCol = this.current.getY();
            int row = y/50;
            int col = x/50;
            if(nextRow==row+1){
                this.move(0,50);}
            if(nextRow==row-1){
                this.move(0,-50);}
            if(nextCol==col+1){
                this.move(50,0);}
            if(nextCol==col-1){
                this.move(-50,0);}
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
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void setX(int x) {
        this.y = x;
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
    public void setDestination(String destination) {
        
    }
   public String getDestination(){
        return null;
    }
}
