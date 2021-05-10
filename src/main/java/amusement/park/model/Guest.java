package amusement.park.model;

import amusement.park.CoinsPanel;
import static amusement.park.GameArea.UNIT_SIZE;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Random;

public class Guest extends Person {

    private final static String[] PHOTOS = {"boy.png", "girl.png", "guy.png", "hippy_boy.png", "small_girl.png"};
    private final static String[] Destinations = {"FirstGame", "SecondGame", "ThirdGame", "SweetShop", "Buffet", "HotDogStand"};
    private final static Random random = new Random();
    public boolean isinside = false;
    public boolean rmv = false;
    private int money = 0;
    private int mood = 0;
    public long eatingTime;
    public boolean hasEaten=false;
    public static CoinsPanel coinsPanel = null;

    public Guest(int money) {
        super(PHOTOS[random.nextInt(4)]);
        this.money = money;
        this.mood = 1000;
    }

    public boolean isDestinationGame() {
        boolean isGame = false;
        if (this.destination.equals("FirstGame")) {
            isGame = true;
        } else if (this.destination.equals("SecondGame")) {
            isGame = true;
        } else if (this.destination.equals("ThirdGame")) {
            isGame = true;
        }
        return isGame;
    }

    public boolean isDestinationRestaurant() {
        boolean isRest=false;
        if(this.destination.equals("Buffet")) {
            isRest=true;
        }
        else if(this.destination.equals("HotDogStand")) {
            isRest=true;
        }
        else if(this.destination.equals("SweetShop")) {
            isRest=true;
        }
        return isRest;
    }
    
    public void fillPocket(int money) {
        this.money = money;
    }

    public void pay(int price) throws NotEnoughMoneyException {
        int auxMoney = money - price;
        if (auxMoney >= 0) {
            //if (coinsPanel != null) {
                this.money = money - price;
                this.money = Math.max(money, 0);
                //coinsPanel.increaseCoins(price);
            //}
        } else {
            throw new NotEnoughMoneyException();
        }
    }

    public void changeMood(int change) {
        int mood = this.mood + change;
        if (mood > 100) {
            this.mood = 100;
        } else {
            this.mood = Math.max(mood, 0);
        }
    }
    
    /**
     * Mood tracker for guests
     * @param graphics 
     */
    public void draw(Graphics graphics) {
        int x = this.x;
        int y = this.y;
        graphics.drawImage(getIcon().getImage(), x, y, UNIT_SIZE, UNIT_SIZE - 15, null);  
        if (this.getMood() > 70 && this.getMood()<=100) {
            graphics.setColor(Color.green);
            ((Graphics2D) graphics).setStroke(new BasicStroke(10));
            graphics.drawLine(x, y, x+UNIT_SIZE , y);
        } else {
            if (this.getMood() > 20 && this.mood <= 70) {
                graphics.setColor(Color.yellow);
                ((Graphics2D) graphics).setStroke(new BasicStroke(10));
                graphics.drawLine(x, y, x+UNIT_SIZE , y);
            } 
            else {
                if (this.getMood() <= 20) {
                    graphics.setColor(Color.red);
                    ((Graphics2D) graphics).setStroke(new BasicStroke(10));
                    graphics.drawLine(x, y, x+UNIT_SIZE, y);
                }
            }
        }
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void call_security() {
        Messagebox.infoBox("Thief is running", "Security is called");
    }

    public void goToATM() {
        this.destination = "ATM";
    }

    public void generateDestination() {
        this.destination = Destinations[random.nextInt(6)];
        System.out.println(destination);
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getMood() {
        return mood;
    }

    public void leaveThePark() {
        this.destination = "PoliceStation";
    }

    public static class NotEnoughMoneyException extends Exception {
        public NotEnoughMoneyException() {
            super("Not enough money");
        }
    }
}
