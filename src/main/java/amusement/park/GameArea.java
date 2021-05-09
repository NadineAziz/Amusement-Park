package amusement.park;

import amusement.park.model.*;
import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.Building;
import amusement.park.model.buildings.PoliceStation;
import amusement.park.model.buildings.ThiefDen;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.pathfinding.PathFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum Direction {
    DOWN, UP, LEFT, RIGHT;
}

public class GameArea extends JPanel {

    public static final int UNIT_SIZE = 50;
    private static final int GAME_AREA_WIDTH = 850;
    private static final int GAME_AREA_HEIGHT = 450;
    private final static Random random = new Random();
    public static int aa = 0;
    public static int bb = 0;
    public final int numberOfRows = GAME_AREA_HEIGHT / UNIT_SIZE;
    public final int numberOfCols = GAME_AREA_WIDTH / UNIT_SIZE;
    private final int entranceMoney = 30;
    private final PlaceManager placeManager;
    private final List<Guest> guests = new ArrayList<>();
    private final List<Thief> thieves = new ArrayList<>();
    private final List<PoliceOfficer> cops = new ArrayList<>();
    private final List<Security> securities = new ArrayList<>();
    public boolean caught = false;
    public boolean thiefIsCaught = false;
    public boolean temp = false;
    public boolean isstolen = false;
    public boolean isThiefInSecBuilding = false;
    public boolean atthesameplace = false;
    public int tempthiefmoneyvariable = Thief.mny;
    Clicklistener click = new Clicklistener();
    JButton startButton;
    long elapsed;
    private int numberOfQuests = 0;
    private int numberOfThieves = 0;
    private int numofcops = 0;
    private int numofsecurities = 0;
    private boolean parkOpen = false;
    private Repairman repairman;
    private BasicBuilding repairmanDest;
    private GamePanel gpanel;

    //NEW THINGS REGARDING MOOD
    private int numOfGarbage = 10;
    private int numOfPlants = 0;

    public int getNumOfGarbage() {
        return numOfGarbage;
    }

    //NEW THIGNS REGARDING MOOD
    public void setNumOfGarbage(int numOfGarbage) {
        this.numOfGarbage = numOfGarbage;
    }

    //NEW THIGNS REGARDING MOOD
    public int getNumOfPlants() {
        return numOfPlants;
    }

    //NEW THIGNS REGARDING MOOD
    public void setNumOfPlants(int numOfPlants) {
        this.numOfPlants = numOfPlants;
    }

    //NEW THIGNS REGARDING MOOD
    public void addGarbage() {
        this.numOfGarbage += 1;
    }

    //NEW THIGNS REGARDING MOOD
    public void addPlant() {
        this.numOfPlants += 1;
    }

    //NEW THIGNS REGARDING MOOD - CHANGE -1,0 AND 1 VALUES IN ORDER TO INCREASE/DECREASE THE MOOD CHANGE
    public int moodChangeAmountPerMove() {
        if (numOfGarbage > numOfPlants) {
            return -1;
        } else if (numOfGarbage == numOfPlants) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Setting game UI
     *
     * @param gamePanel
     */
    public GameArea(GamePanel gamePanel) {
        super();
        this.gpanel = gamePanel;
        startButton = gamePanel.getStartButton();
        placeManager = new PlaceManager(numberOfRows, numberOfCols);

        placeRandomBuildings();
        startButton.addActionListener(click);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gamePanel.getSelectedItem() != null) {
                    BasicBuilding building = gamePanel.getSelectedItem().createBuilding();
                    int indexY = (e.getX() / UNIT_SIZE);
                    int indexX = (e.getY() / UNIT_SIZE);
                    if (placeManager.canBePlaced(indexX, indexY)) {
                        if (gamePanel.hasEnoughMoney() && !placeManager.checkIfGameExists(building)) {
                            gamePanel.buyBuilding();
                            placeManager.addBuilding(building, indexX, indexY);
                            building.startTimer = System.currentTimeMillis();
                        }
                    }
                    repaint();
                } else {
                    System.out.println("Building not selected");
                }
            }
        });

        new Timer(gamePanel.getFps(), new NewFrameListener()).start();

    }

    public int getNumberOfQuests() {
        return numberOfQuests;
    }

    public int getNumofcops() {
        return numofcops;
    }

    public void setNumofcops(int num) {
        this.numofcops = num;
        for (int i = 0; i < numofcops; i++) {
            cops.add(new PoliceOfficer());
        }
    }

    public void setNumOfGuests(int num) {
        this.numberOfQuests = num;
        for (int i = 0; i < numberOfQuests; i++) {
            Guest guest = new Guest(random.nextInt(10000) + 1);
            guest.generateDestination();
            guests.add(guest);
        }
    }

    public void setNumOfThieves(int num) {
        this.numberOfThieves = num;
        for (int i = 0; i < numberOfThieves; i++) {
            thieves.add(new Thief(Thief.skillevel));
        }
    }

    public void setNumOfsecurities(int num) {
        this.numofsecurities = num;
        for (int i = 0; i < numofsecurities; i++) {
            securities.add(new Security());
        }
    }

    public int getDuration(long start) {
        long endTime = System.currentTimeMillis();
        String seconds = ((endTime - start) / 1000) + "";
        return Integer.parseInt(seconds);
    }

    private BasicBuilding repairmanDestExists(String buildingType) {
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (this.placeManager.getPlace(i, j) != null) {
                    if (this.placeManager.getPlace(i, j).getBuildingType().equals(buildingType) && !this.placeManager.getPlace(i, j).isWorking()) {
                        repairmanDest = this.placeManager.getPlace(i, j);
                        return this.placeManager.getPlace(i, j);
                    }

                }
            }
        }
        return null;
    }

    /**
     * Checking if game is broken down and sets destination for the repairman
     */
    private void checkToBreakDown() {
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (this.placeManager.getPlace(i, j) != null) {
                    if (this.placeManager.getPlace(i, j) instanceof BaseGame) {
                        if (getDuration(this.placeManager.getPlace(i, j).startTimer) > 30) {
                            this.placeManager.getPlace(i, j).breakDownTheGame();
                            if (repairman == null) {
                                repairman = new Repairman();
                                this.repairman.setDestination(this.placeManager.getPlace(i, j).getBuildingType());
                                repairman.isCalled = true;
                            } else if (repairman.reachedDestination) {
                                repairman.setDestination(this.placeManager.getPlace(i, j).getBuildingType());
                                repairman.isCalled = true;
                                repairman.reachedDestination = false;
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param game needs to be fixed Repairman fixes game by setting original
     * game picture startTimer starts time so after some time game will be
     * broken again
     */
    private void gameIsFixed(BasicBuilding game) {
        game.setPrevPic();
        game.startTimer = System.currentTimeMillis();
    }

    /**
     * Displays the police station, cave and atm to random empty places
     */
    private void placeRandomBuildings() {
        BasicBuilding policeStation = new PoliceStation();
        int indexX = 0;
        int indexY = 0;
        this.placeManager.addBuilding(policeStation, indexX, indexY);

    }

    public void placecave() {
        BasicBuilding cave = new ThiefDen();
        int i = 0, j = 0;
        boolean found = false;
        while (!found) {
            i = random.nextInt(9);
            j = random.nextInt(9);
            if (this.placeManager.getPlace(i, j) != null) {
                if (this.placeManager.getPlace(i, j).getBuildingType().equals("Path")) {
                    found = true;
                } else {
                    i = random.nextInt(9);
                    j = random.nextInt(9);
                }
            }

        }
        if (this.placeManager.getPlace(i, j).getBuildingType().equals("Path")) {
            tryPlacingCave(cave, i, j);
            thieves.get(0).setX(i * 50);
            thieves.get(0).setY(j * 50);
        }
    }

    private void tryPlacingCave(BasicBuilding building, int indexX, int indexY) {
        while (!this.placeManager.addCave(building, indexX, indexY)) {
            indexX = random.nextInt(numberOfRows - 1);
            indexY = random.nextInt(numberOfCols - 1);
        }
    }

    /**
     * guest moves only in path
     *
     * @param nextX next x position for guest step
     * @param nextY next y position for guest step
     * @return
     */
    public boolean guestMoveInPath(int nextX, int nextY) {
        int row = (nextY / 50);
        int column = (nextX / 50);
        if (this.placeManager.getPlace(row, column) != null) {
            return this.placeManager.getPlace(row, column).getBuildingType().equals("Path")
                    || this.placeManager.getPlace(row, column).getBuildingType().equals("ThiefDen");

        }

        return false;
    }

    /**
     * Moves the guests in the matrix
     */
    public void moveRepairman() {
        PathFinder BFS;
        if (repairman != null && repairmanDestExists(repairman.getDestination()) != null) {
            if (repairman.isCalled) {
                BFS = new PathFinder(this.placeManager, this.repairman);
                repairman.currentPath = BFS.pathExists();
                repairman.isCalled = false;
            }
            repairman.moveTowardsDestination();
            if (repairmanDest != null) {
                int repairManX = repairman.getY() / 50;
                int repairManY = repairman.getX() / 50;
                if (this.placeManager.getPlace(repairManX, repairManY) != null && this.placeManager.getPlace(repairManX, repairManY).getBuildingType().equals(repairman.getDestination())) {
                    gameIsFixed(repairmanDest);
                    repairman.reachedDestination = true;
                }

            }

        } else if (repairman != null) {
            if (repairmanDest == null && !repairman.isCalled && repairman.reachedDestination) {
                repairman.leaveThePark();
            }
        }
    }

    /**
     * Random movement for person
     * @param person Person that will be moved randomly
     */
    public void changeDirection(Person person) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir == Direction.UP) {
            if (person.getY() > 0) {
                if (guestMoveInPath(person.getX(), (person.getY() - 50))) {
                    person.move(0, -50);
                }
            }
        } else if (dir == Direction.DOWN) {
            if (person.getY() < 400) {
                if (guestMoveInPath(person.getX(), person.getY() + 50)) {
                    person.move(0, 50);
                }
            }
        } else if (dir == Direction.LEFT) {
            if (person.getX() > 0) {
                if (guestMoveInPath((person.getX() - 50), person.getY())) {
                    person.move(-50, 0);
                }
            }
        } else if (dir == Direction.RIGHT) {
            if (person.getX() < 800) {
                if (guestMoveInPath(person.getX() + 50, person.getY())) {
                    person.move(50, 0);
                }
            }
        }
    }
    

    /**
     * 
     * @param person Security will be moved randomly but with slower speed
     */
    public void changeDirectionofsecurity(Person person) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir == Direction.UP) {
            if (person.getY() > 0) {
                if (guestMoveInPath(person.getX(), (person.getY() - 10))) {
                    person.move(0, -10);
                }
            }
        } else if (dir == Direction.DOWN) {
            if (person.getY() < 400) {
                if (guestMoveInPath(person.getX(), person.getY() + 10)) {
                    person.move(0, 10);
                }
            }
        } else if (dir == Direction.LEFT) {
            if (person.getX() > 0) {
                if (guestMoveInPath((person.getX() - 10), person.getY())) {
                    person.move(-10, 0);
                }
            }
        } else if (dir == Direction.RIGHT) {
            if (person.getX() < 800) {
                if (guestMoveInPath(person.getX() + 10, person.getY())) {
                    person.move(10, 0);
                }
            }
        }
    }

    public void moveAllGuests() {
        this.guests.forEach(guest -> {
            if (!guest.rmv) {
                //if guest has destination, move to the destination
                if (guest.getDestination() == null || !this.placeManager.buildingExists(guest.getDestination()) || guest.reachedDestination) {
                    if (guest.getMoney() <= 0) {
                        guest.goToATM();
                    } else if (guest.getMood() <= 0) {
                        guest.leaveThePark();
                    } else {
                        guest.generateDestination();
                    }
                    if (guest.reachedDestination) {
                        //mood increaser if they went to game or restaurant
                        guest.changeMood(10);
                        guest.reachedDestination = false;
                    }
                    PathFinder BFSFinder = new PathFinder(this.placeManager, guest);
                    guest.currentPath = BFSFinder.pathExists();
                }
                if (this.placeManager.buildingExists(guest.getDestination())) {
                    guest.moveTowardsDestination();
                    System.out.println("guest position: " + guest.getY() / 50 + " " + guest.getX() / 50);
                    int guestX = guest.getY() / 50;
                    int guestY = guest.getX() / 50;
                    if (placeManager.getPlace(guestX, guestY) != null) {
                        if (this.placeManager.getPlace(guestX, guestY).getBuildingType().equals(guest.getDestination())) {
                            BasicBuilding destinationBuilding = this.placeManager.getPlace(guest.getY() / 50, guest.getX() / 50);
                            guest.reachedDestination = true;
                            if (guest.getMood() <= 0) {
                                guest.rmv = true;
                            } else {
                                System.out.println("Destination reached");
                                if (destinationBuilding instanceof Building) {
                                    ((Building) destinationBuilding).performAction(guest);
                                }
                            }
                            if (this.placeManager.getPlace(guest.getY() / 50, guest.getX() / 50).getBuildingType().equals("ATM")) {
                                int num = random.nextInt(100) + 101;
                                guest.fillPocket(num);
                                System.out.println("Cash withdrawal is done!");
                                changeDirection(guest);

                            }
                            if (!this.placeManager.getPlace(guest.getY() / 50, guest.getX() / 50).getBuildingType().equals("ATM")) {
                                gpanel.payEntranceFee(10);
                            }
                            try {
                                guest.pay(10);
                            } catch (Guest.NotEnoughMoneyException ignored) {
                            }

                            if (!this.placeManager.getPlace(guest.getY() / 50, guest.getX() / 50).getBuildingType().equals("ATM")) {
                                guest.changeMood(10);
                            }
                            if (guest.getMood() <= 0) {
                                System.out.println("Mood tanked");
                            }

                        }
                    }
                } else {
                    changeDirection(guest);
                }
                int amountOfMoodChange = this.moodChangeAmountPerMove();
                System.out.println(guest.getMood());
                guest.changeMood(amountOfMoodChange);
                System.out.println(guest.getMood());
            }
        });
    }

    public void moveAllThieves() {

        // steal();
        catchthethief();
        this.thieves.forEach(thief -> {
            PathFinder BFSFinder;
            if (this.placeManager.getPlace(thief.getY() / 50, thief.getX() / 50).getBuildingType().equals(thief.getDestination())) {

                thief.reachedDestination = true;
            }

            if (thief.getDestination() == null && thief.stealMoney) {
                thief.run();
                BFSFinder = new PathFinder(this.placeManager, thief);
                thief.currentPath = BFSFinder.pathExists();
            } else if (thief.reachedDestination) {
                thief.reachedDestination = false;
                atthesameplace = true;
                thief.thiefgoestopolstation();
                BFSFinder = new PathFinder(this.placeManager, thief);
                thief.currentPath = BFSFinder.pathExists();

            } else if (thief.getDestination() == null && thief.isCaught) {
                thief.thiefgoestosecbuilding();
                BFSFinder = new PathFinder(this.placeManager, thief);
                thief.currentPath = BFSFinder.pathExists();
            }
            if (thiefIsCaught) {
                thief.moveTowardsDestination();
            } else if (thief.stealMoney) {
                thief.moveTowardsDestination();
            } else if (atthesameplace) {
                thief.moveTowardsDestination();
            } else if (thief.isCaught) {
                thief.moveTowardsDestination();
            } else {
                changeDirection(thief);
            }

        });

    }

    public void movethieftothesb() {
        this.thieves.forEach(thief -> {
            PathFinder BFSFinder;
            if (thief.getDestination() == null || !this.placeManager.buildingExists(thief.getDestination()) || thief.reachedDestination) {
                thief.thiefgoestosecbuilding();
                if (thief.reachedDestination) {
                    thief.reachedDestination = false;

                }
                BFSFinder = new PathFinder(this.placeManager, thief);
                thief.currentPath = BFSFinder.pathExists();

            }

            if (this.placeManager.buildingExists(thief.getDestination())) {
                thief.moveTowardsDestination();
                if (this.placeManager.getPlace(thief.getY() / 50, thief.getX() / 50) != null) {
                    if (this.placeManager.getPlace(thief.getY() / 50, thief.getX() / 50).getBuildingType().equals(thief.getDestination())) {

                        thief.reachedDestination = true;
                    }
                }
            } else {
                changeDirection(thief);
            }
        });
    }

    public void movethieftotheps() {
        this.thieves.forEach(thief -> {
            PathFinder BFSFinder;
            if (thief.getDestination() == null || !this.placeManager.buildingExists(thief.getDestination()) || thief.reachedDestination) {
                thief.thiefgoestopolstation();
                if (thief.reachedDestination) {
                    thief.reachedDestination = false;

                }
                BFSFinder = new PathFinder(this.placeManager, thief);
                thief.currentPath = BFSFinder.pathExists();

            }

            if (this.placeManager.buildingExists(thief.getDestination())) {
                thief.moveTowardsDestination();
                if (this.placeManager.getPlace(thief.getY() / 50, thief.getX() / 50) != null) {
                    if (this.placeManager.getPlace(thief.getY() / 50, thief.getX() / 50).getBuildingType().equals(thief.getDestination())) {
                        thief.reachedDestination = true;
                    }
                }
            } else {
                changeDirection(thief);
            }
        });
    }

    public void movecopstotheps() {
        this.cops.forEach(PoliceOfficer -> {
            PathFinder BFSFinder;
            if (PoliceOfficer.getDestination() == null || !this.placeManager.buildingExists(PoliceOfficer.getDestination()) || PoliceOfficer.reachedDestination) {
                PoliceOfficer.policegoestopolstation();
                if (PoliceOfficer.reachedDestination) {
                    PoliceOfficer.reachedDestination = false;

                }
                BFSFinder = new PathFinder(this.placeManager, PoliceOfficer);
                PoliceOfficer.currentPath = BFSFinder.pathExists();

            }

            if (this.placeManager.buildingExists(PoliceOfficer.getDestination())) {
                PoliceOfficer.moveTowardsDestination();
                if (this.placeManager.getPlace(PoliceOfficer.getY() / 50, PoliceOfficer.getX() / 50) != null) {
                    if (this.placeManager.getPlace(PoliceOfficer.getY() / 50, PoliceOfficer.getX() / 50).getBuildingType().equals(PoliceOfficer.getDestination())) {

                        PoliceOfficer.reachedDestination = true;

                    }
                }
            } else {
                changeDirection(PoliceOfficer);
            }
        });
    }

    public void movesecuritytothesb() {
        this.securities.forEach(Security -> {

            PathFinder BFSFinder;
            if (Security.getDestination() == null || !this.placeManager.buildingExists(Security.getDestination()) || Security.reachedDestination) {
                Security.setDestination("SecurityBuilding");
                System.out.println("BB:" + Security.getDestination());
                if (Security.reachedDestination) {
                    Security.reachedDestination = false;

                }
                BFSFinder = new PathFinder(this.placeManager, Security);
                Security.currentPath = BFSFinder.pathExists();

            }

            if (this.placeManager.buildingExists(Security.getDestination())) {

                Security.moveTowardsDestination();
                if (this.placeManager.getPlace(Security.getY() / 50, Security.getX() / 50) != null) {
                    if (this.placeManager.getPlace(Security.getY() / 50, Security.getX() / 50).getBuildingType().equals(Security.getDestination())) {
                        Security.reachedDestination = true;
                    }
                }
            } else {
                changeDirectionofsecurity(Security);
            }
        });
    }

    public void movecopstothesb() {
        this.cops.forEach(PoliceOfficer -> {
            PathFinder BFSFinder;
            if (PoliceOfficer.getDestination() == null || !this.placeManager.buildingExists(PoliceOfficer.getDestination()) || PoliceOfficer.reachedDestination) {
                PoliceOfficer.policegoestosecuritybuilding();
                if (PoliceOfficer.reachedDestination) {
                    PoliceOfficer.reachedDestination = false;

                }
                BFSFinder = new PathFinder(this.placeManager, PoliceOfficer);
                PoliceOfficer.currentPath = BFSFinder.pathExists();

            }

            if (this.placeManager.buildingExists(PoliceOfficer.getDestination())) {
                PoliceOfficer.moveTowardsDestination();
                if (this.placeManager.getPlace(PoliceOfficer.getY() / 50, PoliceOfficer.getX() / 50) != null) {
                    if (this.placeManager.getPlace(PoliceOfficer.getY() / 50, PoliceOfficer.getX() / 50).getBuildingType().equals(PoliceOfficer.getDestination())) {

                        PoliceOfficer.reachedDestination = true;
                    }
                }
            } else {
                changeDirection(PoliceOfficer);
            }
        });
    }

    public void moveAllcops() {
        this.cops.forEach(PoliceOfficer -> {
            PathFinder BFSFinder;
            if (this.placeManager.getPlace(PoliceOfficer.getY() / 50, PoliceOfficer.getX() / 50).getBuildingType().equals(PoliceOfficer.getDestination())) {

                PoliceOfficer.reachedDestination = true;
            }
            if (PoliceOfficer.getDestination() == null && isThiefInSecBuilding) {
                PoliceOfficer.policegoestosecuritybuilding();
                BFSFinder = new PathFinder(this.placeManager, PoliceOfficer);
                PoliceOfficer.currentPath = BFSFinder.pathExists();
            }
            if (PoliceOfficer.reachedDestination) {
                PoliceOfficer.reachedDestination = false;
                atthesameplace = true;
                PoliceOfficer.policegoestopolstation();
                BFSFinder = new PathFinder(this.placeManager, PoliceOfficer);
                PoliceOfficer.currentPath = BFSFinder.pathExists();

            }

            if (isThiefInSecBuilding) {
                PoliceOfficer.moveTowardsDestination();
            } else if (atthesameplace) {
                PoliceOfficer.moveTowardsDestination();
            } else {
                changeDirection(PoliceOfficer);
            }
        });
    }

    public void moveAllsecurities() {

        this.securities.forEach(Security -> {
            PathFinder BFSFinder;
            if (Security.getDestination() == null && temp) {
                Security.seccuritygoestosb();
                BFSFinder = new PathFinder(this.placeManager, Security);
                Security.currentPath = BFSFinder.pathExists();
            }
            if (temp) {
                Security.moveTowardsDestination();
            } else {
                changeDirection(Security);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        this.guests.forEach(guest -> {
            guest.draw(g);
        });
        this.thieves.forEach(thief -> {
            thief.draw(g);
        });
        this.cops.forEach(PoliceOfficer -> {
            PoliceOfficer.draw(g);
        });
        this.securities.forEach(Security -> {
            Security.draw(g);
        });
        if (this.repairman != null) {
            repairman.draw(g);
        }
//          Display a grid
        for (int i = 0; i <= GAME_AREA_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, GAME_AREA_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, GAME_AREA_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (this.placeManager.getPlace(i, j) != null) {
                    BasicBuilding building = this.placeManager.getPlace(i, j);
                    if (building.getSize() > 1) {
                        if (j + (building.getSize() - 1) < numberOfCols
                                && i + (building.getSize() - 1) < numberOfRows) {
                            if (building.equals(this.placeManager.getPlace(i, j + (building.getSize() - 1)))
                                    && (building.equals(this.placeManager.getPlace(i + (building.getSize() - 1), j)))) {
                                building.draw(g, j * UNIT_SIZE, i * UNIT_SIZE);
                            }
                        }
                    } else {
                        building.draw(g, j * UNIT_SIZE, i * UNIT_SIZE);
                    }

                }
            }
        }
    }

    /**
     * Thief steal money from Guest, if they are on the same tile, and thief's skillLevel is high enough.
     */
    public void steal() {
        for (Thief thief : thieves) {
            for (Guest guest : guests) {
                if (guest.getX() == thief.getX() && guest.getY() == thief.getY()) {

                    Random rnd = new Random();
                    int randomnumber = rnd.nextInt(100) + 1;
                    if (Thief.getSkillevel() > randomnumber) {
                        System.out.println("Money is stolen");
                    } else {
                        System.out.println("Thief is running");
                        thief.stealMoney = true;

                    }
                    tempthiefmoneyvariable = tempthiefmoneyvariable + randomnumber;

                }

            }

        }

    }

    /**
     *  Security will try catch the Thief that has stolen money from guests. 
     */
    public void catchthethief() {
        for (Thief thief : thieves) {
            for (Security security : securities) {
                if (security.getX() == thief.getX() && security.getY() == thief.getY()) {
                    System.out.println("Thief is caught");
                    thief.isCaught = true;
                    thiefIsCaught = true;
                    temp = true;
                    if (thief.getDestination() != null) {
                        isThiefInSecBuilding = true;
                        System.out.println("Police is called");
                    }
                }
            }
        }
    }

    /**
     * startButton actionListener is used to openGame, and guests and others can get in park.
     */
    private class Clicklistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!parkOpen) {
                setNumOfGuests(3);
                parkOpen = true;
                setNumOfThieves(1);
                setNumofcops(1);
                setNumOfsecurities(1);
                placecave();
                gpanel.payEntranceFee(getNumberOfQuests() * entranceMoney);
            }
            parkOpen = true;

        }
    }

    /**
     * moves all people, Timer calls NewFrameListener.
     */
    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            moveAllGuests();
            moveAllThieves();
            checkToBreakDown();
            if (isThiefInSecBuilding) {
                moveAllcops();
            }
            moveAllsecurities();
            moveRepairman();
            repaint();

        }
    }

}
