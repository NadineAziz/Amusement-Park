package amusement.park.model;

import static amusement.park.GameArea.*;
import amusement.park.pathfinding.Node;

import java.awt.*;
import java.util.List;
import java.util.Random;


public class Guest extends Person {
    private final static String[] PHOTOS = {"boy.png", "girl.png", "guy.png", "hippy_boy.png", "small_girl.png"};
    private final static String[] Destinations= {"FirstGame", "SecondGame", "ThirdGame", "SweetShop", "Buffet", "HotDogStand"};
    private final static Random random = new Random();

    private String destination;
    private int money=random.nextInt(10000)+1;
    private int mood = 50;
    

    public Guest(int money) {
        super(PHOTOS[random.nextInt(4)]);
        this.money = money;
    }

    public void pay(int price) {
        money = money - price;
        this.money = Math.max(money, 0);
    }

    public void changeMood(int change) {
        int mood = this.mood + change;
        if (mood > 100) {
            this.mood = 100;
        } else this.mood = Math.max(mood, 0);
    }


    public void call_security() {
        Messagebox.infoBox("Thief is running", "Security is called");

    }
    
    public void generateDestination(){
        this.destination = Destinations[random.nextInt(6)];
    }
    
    public String getDestination(){
        return this.destination;
    }

}
