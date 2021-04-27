package amusement.park.model;

import java.util.Random;

public class Guest extends Person {

    private final static String[] PHOTOS = {"boy.png", "girl.png", "guy.png", "hippy_boy.png", "small_girl.png"};
    private final static String[] Destinations = {"FirstGame", "SecondGame", "ThirdGame", "SweetShop", "Buffet", "HotDogStand"};
    private final static Random random = new Random();

    //private String destination;
    private int money = 0;
    private int mood = 0;
    public boolean isinside = false;
    public boolean rmv = false;

    public Guest(int money) {
        super(PHOTOS[random.nextInt(4)]);
        this.money = 0;
        this.mood = 100000;
    }

    public void setMoney(int money) {
        this.money = money;
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

    public void pay(int price) {
        money = money - price;
        this.money = Math.max(money, 0);
    }

    public void changeMood(int change) {
        int mood = this.mood + change;
        if (mood > 100) {
            this.mood = 100;
        } else {
            this.mood = Math.max(mood, 0);
        }
    }

    public void waitForIt(int seconds) {
        long start = System.currentTimeMillis();
        // some time passes
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        System.out.println(elapsedTime);
    }

    public int getMoney() {
        return money;
    }

    public void call_security() {
        Messagebox.infoBox("Thief is running", "Security is called");

    }

    public void goToATM() {
        this.destination = "ATM";
    }

    public void generateDestination() {
        this.destination = Destinations[random.nextInt(6)];
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    public int getMood() {
        return mood;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void leavethepark() {
        this.destination = "PoliceStation";
    }
}
