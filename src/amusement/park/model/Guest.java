package amusement.park.model;

import static amusement.park.GameArea.*;

import java.awt.*;
import java.util.Random;

public class Guest extends Person {
     private final static String[] PHOTOS = {"boy.png", "girl.png", "guy.png", "hippy_boy.png", "small_girl.png"};
    private final static Random random = new Random();

    private int money;
    private int mood = 50;
    private int x;
    private int y;

    public Guest(int money) {
        super(PHOTOS[random.nextInt(4)]);
        this.money = money;
    }

    public void pay(int price) {
        int money = this.money - price;
        this.money = Math.max(money, 0);
    }

    public void changeMood(int change) {
        int mood = this.mood + change;
        if (mood > 100) {
            this.mood = 100;
        } else this.mood = Math.max(mood, 0);
    }

    private void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void changeDirection() {
        switch (Direction.values()[random.nextInt(4)]) {
            case UP:
                move(1, 0);
                break;
            case DOWN:
                move(-1, 0);
                break;
            case LEFT:
                move(0, 1);
                break;
            case RIGHT:
                move(0, -1);
                break;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        ((Graphics2D) g).setStroke(new BasicStroke(5));
        g.drawLine(x, y, (UNIT_SIZE * mood) / 100, y);
        g.drawImage(getIcon().getImage(), x, y, UNIT_SIZE, UNIT_SIZE - 15, null);
    }

    public void call_security() {

    }

    enum Direction {
        DOWN, UP, LEFT, RIGHT;
    }

}
