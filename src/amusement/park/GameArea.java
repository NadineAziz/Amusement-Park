package amusement.park;

import amusement.park.model.Guest;
import amusement.park.model.Person;
import amusement.park.model.buildings.*;
import amusement.park.model.buildings.games.*;
import amusement.park.pathfinding.*;
import amusement.park.model.buildings.ATM;
import amusement.park.model.buildings.BasicBuilding;
import amusement.park.model.buildings.Building;
import amusement.park.model.buildings.Path;
import amusement.park.model.buildings.PoliceStation;
import amusement.park.model.buildings.ThiefDen;
import amusement.park.model.buildings.games.BaseGame;
import amusement.park.model.buildings.games.FirstGame;
import amusement.park.model.buildings.games.SecondGame;
import amusement.park.model.buildings.games.ThirdGame;
import amusement.park.CoinsPanel;
import amusement.park.model.Messagebox;
import amusement.park.model.PoliceOfficer;
import amusement.park.model.Repairman;
import amusement.park.model.Security;
//import amusement.park.model.SecurityBuilding;
import amusement.park.model.Thief;
import amusement.park.model.Cleaner;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import static javax.management.Query.value;

enum Direction {
    DOWN, UP, LEFT, RIGHT;
}

public class GameArea extends JPanel {

    public static final int UNIT_SIZE = 50;
    private static final int GAME_AREA_WIDTH = 850;
    private static final int GAME_AREA_HEIGHT = 450;
    private final static Random random = new Random();
    private int numberOfQuests = 0;
    private int numberOfThieves = 0;
    private int numofcops = 0;

    private int numofsecurities = 0;
    private final int Entrancemoney = 30;
    public final BasicBuilding[][] placesMatrix;
    public final int numberOfRows = GAME_AREA_HEIGHT / UNIT_SIZE;
    public final int numberOfCols = GAME_AREA_WIDTH / UNIT_SIZE;
    private boolean parkOpen = false;
    public boolean caught = false;
    public boolean thiefIsCaught= false;
    public boolean temp = false;
    public boolean isstolen = false;
    public boolean isThiefInSecBuilding = false;
    public boolean atthesameplace = false;
    public static int aa=0;
    public static int bb=0;
    public int tempthiefmoneyvariable = Thief.mny;
    private final List<Guest> guests = new ArrayList<>();
    private final List<Thief> thieves = new ArrayList<>();
    private final List<PoliceOfficer> cops = new ArrayList<>();
    private final List<Security> securities = new ArrayList<>();
    private Repairman repairman;
    private Cleaner cleaner;
    private BasicBuilding repairmanDest;
    Clicklistener click = new Clicklistener();
    JButton startButton;
    //long start;
    long elapsed;

    private GamePanel gpanel;

    public GameArea(GamePanel gamePanel) {
        super();
        this.gpanel = gamePanel;
        startButton = gamePanel.getStartButton();
        placesMatrix = new BasicBuilding[numberOfRows][numberOfCols];

        placeRandomBuildings();
        startButton.addActionListener(click);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gamePanel.getSelectedItem() != null) {
                    BasicBuilding building = gamePanel.getSelectedItem().createBuilding();
                    int indexY = (e.getX() / UNIT_SIZE);
                    int indexX = (e.getY() / UNIT_SIZE);
                    if (canBePlaced(indexX, indexY)) {
                        if (gamePanel.hasEnoughMoney() && !checkIfGameExists(building)) {
                            gamePanel.buyBuilding();

                            addBuilding(building, indexX, indexY);
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

    private boolean canBePlaced(int indexX, int indexY) {
        if (indexX < numberOfRows && indexY < numberOfCols) {
            return placesMatrix[indexX][indexY] == null;
        } else {
            return false;
        }
    }
    private boolean canCaveBePlaced(int indexX, int indexY) {
        if (indexX < numberOfRows && indexY < numberOfCols) {
            return placesMatrix[indexX][indexY].getBuildingType().equals("Path");
        } else {
            return false;
        }
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
            guests.add(new Guest(random.nextInt(10000) + 1));
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

    private boolean isEnoughSpace(BasicBuilding building, int startX, int startY) {
        if ((startX + building.getSize() <= numberOfRows) && (startY + building.getSize() <= numberOfCols)) {
            for (int x = startX; x < (startX + building.getSize()); x++) {
                for (int y = startY; y < (startY + building.getSize()); y++) {
                    if (!canBePlaced(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
    private boolean isEnoughSpaceForCave(BasicBuilding building, int startX, int startY) {
        if ((startX + building.getSize() <= numberOfRows) && (startY + building.getSize() <= numberOfCols)) {
            for (int x = startX; x < (startX + building.getSize()); x++) {
                for (int y = startY; y < (startY + building.getSize()); y++) {
                    if (!canCaveBePlaced(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean addBuilding(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            if (canBePlaced(indexX, indexY) && isEnoughSpace(building, indexX, indexY)) {
                for (int x = indexX; x < (indexX + building.getSize()); x++) {
                    for (int y = indexY; y < (indexY + building.getSize()); y++) {
                        placesMatrix[x][y] = building;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private boolean addCave(BasicBuilding building, int indexX, int indexY) {
        if (!checkIfGameExists(building)) {
            if (canCaveBePlaced(indexX, indexY) && isEnoughSpaceForCave(building, indexX, indexY)) {
                for (int x = indexX; x < (indexX + building.getSize()); x++) {
                    for (int y = indexY; y < (indexY + building.getSize()); y++) {
                        placesMatrix[x][y] = building;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
 

    private boolean checkIfGameExists(BasicBuilding building) {
        if (building instanceof BaseGame) {
            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfCols; j++) {
                    if (building instanceof FirstGame && placesMatrix[i][j] instanceof FirstGame) {
                        return true;
                    }

                    if (building instanceof SecondGame && placesMatrix[i][j] instanceof SecondGame) {
                        return true;
                    }

                    if (building instanceof ThirdGame && placesMatrix[i][j] instanceof ThirdGame) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getDuration(long start) {
        long endTime = System.currentTimeMillis();
        String seconds = ((endTime - start) / 1000) + "";
        return Integer.parseInt(seconds);
    }

    private boolean buildingExists(String buildingType) {
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    if (placesMatrix[i][j].getBuildingType().equals(buildingType) && placesMatrix[i][j].isWorking()) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    private BasicBuilding repairmanDestExists(String buildingType) {
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    if (placesMatrix[i][j].getBuildingType().equals(buildingType) && !placesMatrix[i][j].isWorking()) {
                        repairmanDest = placesMatrix[i][j];
                        return placesMatrix[i][j];
                    }

                }
            }
        }
        return null;
    }

    private void checkToBreakDown() {

        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    if (placesMatrix[i][j] instanceof BaseGame) {
                        if (getDuration(placesMatrix[i][j].startTimer) > 30) {
                            placesMatrix[i][j].breakDownTheGame();
                            if (repairman == null) {
                                repairman = new Repairman();
                                this.repairman.setDestination(placesMatrix[i][j].getBuildingType());
                                repairman.isCalled = true;
                            } else if (repairman.reachedDestination) {
                                repairman.setDestination(placesMatrix[i][j].getBuildingType());
                                repairman.isCalled = true;
                                repairman.reachedDestination = false;

                            }

                        }
                    }
                }
            }
        }
    }

    private void gameIsFixed(BasicBuilding game) {
        game.setPrevPic();
        game.startTimer = System.currentTimeMillis();
    }

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
               // placesecurity();
                gpanel.payentrancefee(getNumberOfQuests() * Entrancemoney);
            }
            parkOpen = true;

        }
    }

    /**
     * Displays the police station, cave and atm to random empty places
     */
    private void placeRandomBuildings() {
        BasicBuilding policeStation = new PoliceStation();
        //BasicBuilding security = new SecurityBuilding();
        //BasicBuilding atm = new ATM();
        int indexX = 0;
        int indexY = 0;
        addBuilding(policeStation, indexX, indexY);
        //addBuilding(security, indexX, indexY + 15);

        //addBuilding(cave, indexX + 5, indexY);
        //tryPlacingBuilding(atm, indexX, indexY);

    }
    public void placecave(){
    BasicBuilding cave = new ThiefDen();
     int i=0,j=0;
       /*
       System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
       for(int k=0;k<i+j;k++){
           if(placesMatrix[i][j]!=null)
             {
        if (!placesMatrix[i][j].getBuildingType().equals("Path"))
        {
                i=random.nextInt(10)+1;
                j=random.nextInt(10)+1;
        }
        else
        {
             
        break;
        }
             }
       }*/
       boolean found= false;
       while(!found){
           i=random.nextInt(9);
            j=random.nextInt(9);
            //System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
           if(placesMatrix[i][j]!=null){
                if (placesMatrix[i][j].getBuildingType().equals("Path")){
                    found = true;
                } else{
                    i=random.nextInt(9);
                    j=random.nextInt(9);
                }
           } 
           
       }
        if(placesMatrix[i][j].getBuildingType().equals("Path"))
            {
                tryPlacingCave(cave, i, j);
                thieves.get(0).setX(i*50);
                thieves.get(0).setY(j*50);
                //System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
            }
        
            
        
        
     
        System.out.println("indeks "+thieves.get(0).getX());
        System.out.println("indeks "+thieves.get(0).getY());
  
        
    }
    
    public void placesecurity(){
    //BasicBuilding securityb = new SecurityBuilding();
     int i=0,j=0;
       /*
       System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
       for(int k=0;k<i+j;k++){
           if(placesMatrix[i][j]!=null)
             {
        if (!placesMatrix[i][j].getBuildingType().equals("Path"))
        {
                i=random.nextInt(10)+1;
                j=random.nextInt(10)+1;
        }
        else
        {
             
        break;
        }
             }
       }*/
       boolean found= false;
       while(!found){
           i=random.nextInt(8);
            j=random.nextInt(8);
            //System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
           if(placesMatrix[i][j]!=null){
                
                    i=random.nextInt(9);
                    j=random.nextInt(9);
                
           } 
           
       }
      
                tryPlacingBuilding(securityb, i, j);
                securities.get(0).setX(i*50);
                securities.get(0).setY(j*50);
                //System.out.println("aaaaaaaaaaaaaa"+i+" "+j);
            
        
            
        
        
     
        System.out.println("indeks "+securities.get(0).getX());
        System.out.println("indeks "+securities.get(0).getY());
  
        
    }
    private void tryPlacingBuilding(BasicBuilding building, int indexX, int indexY) {
        while (!addBuilding(building, indexX, indexY)) {
            indexX = random.nextInt(numberOfRows - 1);
            indexY = random.nextInt(numberOfCols - 1);
        }
    }
    private void tryPlacingCave(BasicBuilding building, int indexX, int indexY) {
        while (!addCave(building, indexX, indexY)) {
            indexX = random.nextInt(numberOfRows - 1);
            indexY = random.nextInt(numberOfCols - 1);
        }
    }
    /**
     * guest moves only in path
     */
    public boolean guestMoveInPath(int nextX, int nextY) {
        int row = (nextY / 50);
        int column = (nextX / 50);
        if (placesMatrix[row][column] != null) {
            if(placesMatrix[row][column].getBuildingType().equals("Path")||placesMatrix[row][column].getBuildingType().equals("ThiefDen")){
                return true;
            }
           
        }

        return false;
    }

    /**
     * Moves the guests in the matrix
     */
    public void moveRepairman() {
        pathFinder BFS;
        if (repairman != null && repairmanDestExists(repairman.getDestination()) != null) {
            if (repairman.isCalled) {
                BFS = new pathFinder(placesMatrix, this.repairman);
                List<Node> currentPath = BFS.pathExists();
                repairman.currentPath = currentPath;
                repairman.isCalled = false;
            }
            repairman.getPosition();
            if (repairmanDest != null) {
                if (placesMatrix[repairman.getY() / 50][repairman.getX() / 50].getBuildingType().equals(repairman.getDestination())) {
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

    public void changeDirection(Person guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir == Direction.UP) {
            if (guest.getY() > 0) {
                if (guestMoveInPath(guest.getX(), (guest.getY() - 50))) {
                    guest.move(0, -50);
                }
            }
        } else if (dir == Direction.DOWN) {
            if (guest.getY() < 400) {
                if (guestMoveInPath(guest.getX(), guest.getY() + 50)) {
                    guest.move(0, 50);
                }
            }
        } else if (dir == Direction.LEFT) {
            if (guest.getX() > 0) {
                if (guestMoveInPath((guest.getX() - 50), guest.getY())) {
                    guest.move(-50, 0);
                }
            }
        } else if (dir == Direction.RIGHT) {
            if (guest.getX() < 800) {
                if (guestMoveInPath(guest.getX() + 50, guest.getY())) {
                    guest.move(50, 0);
                }
            }
        }
    }

    public void changeDirectionofsecurity(Person guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir == Direction.UP) {
            if (guest.getY() > 0) {
                if (guestMoveInPath(guest.getX(), (guest.getY() - 10))) {
                    guest.move(0, -10);
                }
            }
        } else if (dir == Direction.DOWN) {
            if (guest.getY() < 400) {
                if (guestMoveInPath(guest.getX(), guest.getY() + 10)) {
                    guest.move(0, 10);
                }
            }
        } else if (dir == Direction.LEFT) {
            if (guest.getX() > 0) {
                if (guestMoveInPath((guest.getX() - 10), guest.getY())) {
                    guest.move(-10, 0);
                }
            }
        } else if (dir == Direction.RIGHT) {
            if (guest.getX() < 800) {
                if (guestMoveInPath(guest.getX() + 10, guest.getY())) {
                    guest.move(10, 0);
                }
            }
        }
    }

    public void moveAllGuests() {
        this.guests.forEach(guest -> {

            if (guest.rmv == false) {
                pathFinder BFSFinder;

                //if guest has destination, move to the destination
                if (guest.getDestination() == null || !buildingExists(guest.getDestination()) || guest.reachedDestination) {
                    //checkToBreakDown();
                    if (guest.getMoney() <= 0) {
                        guest.goToATM();
                    } else if (guest.getMood() <= 0) {
                        guest.leavethepark();
                        //guest.reachedDestination=true;

                    } else {
                        guest.generateDestination();
                    }
                    if (guest.reachedDestination) {
                        guest.reachedDestination = false;

                    }
                    BFSFinder = new pathFinder(placesMatrix, guest);
                    List<Node> currentPath = BFSFinder.pathExists();
                    guest.currentPath = currentPath;
                    //start = System.currentTimeMillis();
                }
                if (buildingExists(guest.getDestination())) {
                    guest.getPosition();
                    if (this.placesMatrix[guest.getY() / 50][guest.getX() / 50].getBuildingType().equals(guest.getDestination())) {

                        guest.reachedDestination = true;

                        if (guest.getMood() <= 0) {
                            guest.rmv = true;
                        }

                        //increaseCounter();
                        if (this.placesMatrix[guest.getY() / 50][guest.getX() / 50].getBuildingType().equals("ATM")) {
//                            String value = JOptionPane.showInputDialog(
//                                    GameArea.this,
//                                    "Amount of money:",
//                                    0
//                            );
                             int num=random.nextInt(100)+101;
                             guest.pay(-num);
                            System.out.println("Cash withdrawal is done!");
                            changeDirection(guest);

                        }
                        //guest.pay(10);
                        if (!this.placesMatrix[guest.getY() / 50][guest.getX() / 50].getBuildingType().equals("ATM")) {
                            gpanel.payentrancefee(10);
                        }
                        //guest.changeMood(10);
                        int guestInBuildingSecs;
                        guestInBuildingSecs = this.placesMatrix[guest.getY() / 50][guest.getX() / 50].getTurnTime();
                        // elapsed = System.currentTimeMillis() - start;
                        // if(elapsed>guestInBuildingSecs*1000){
                        //     guest.reachedDestination = true;
                        // }
                        // System.out.println("elapsed secs "+ elapsed/1000);
                        guest.pay(10);
                        if (!this.placesMatrix[guest.getY() / 50][guest.getX() / 50].getBuildingType().equals("ATM")) {
                            guest.changeMood(10);
                        }
                        if (guest.getMood() <= 0) {
                            System.out.println("Mood tanked");
                        }

                    }
                } else {
                    changeDirection(guest);
                }
            }
        });
    }

    public void moveAllThieves() {

       // steal();
        catchthethief();
        this.thieves.forEach(thief -> {
            pathFinder BFSFinder;
            if (this.placesMatrix[thief.getY() / 50][thief.getX() / 50].getBuildingType().equals(thief.getDestination())) {

                thief.reachedDestination = true;
            }
//            if(thiefIsCaught==true)
//            { 
//                
//                 System.out.println("girirem22222");
//                  
//                  thief.thiefgoestosecbuilding();
//                BFSFinder = new pathFinder(placesMatrix, thief);
//                List<Node> currentPath = BFSFinder.pathExists();
//                thief.currentPath = currentPath;
//            }
           // else
                if (thief.getDestination() == null && thief.stealMoney) {
                thief.run();
                BFSFinder = new pathFinder(placesMatrix, thief);
                List<Node> currentPath = BFSFinder.pathExists();
                thief.currentPath = currentPath;
            } else if (thief.reachedDestination == true) {
                thief.reachedDestination = false;
                atthesameplace = true;
                thief.thiefgoestopolstation();
                BFSFinder = new pathFinder(placesMatrix, thief);
                List<Node> currentPath = BFSFinder.pathExists();
                thief.currentPath = currentPath;

            } else if (thief.getDestination() == null && thief.isCaught) {
                //  setNumofcops(1);
                thief.thiefgoestosecbuilding();
                BFSFinder = new pathFinder(placesMatrix, thief);
                List<Node> currentPath = BFSFinder.pathExists();
                thief.currentPath = currentPath;
            }
            ////  if(!thief.stayThere){
            if (thiefIsCaught == true) {
                thief.getPosition();
            }
            else if (thief.stealMoney == true) {
                thief.getPosition();
            } else if (atthesameplace) {
                thief.getPosition();
            } else if (thief.isCaught == true) {
                thief.getPosition();
            }  
            else {
                changeDirection(thief);
            }
            //  }

        });
    
    }

    public void movethieftothesb() {
        this.thieves.forEach(thief -> {
            //if(!thief.stayThere){

            pathFinder BFSFinder;
            if (thief.getDestination() == null || !buildingExists(thief.getDestination()) || thief.reachedDestination) {
                thief.thiefgoestosecbuilding();
                if (thief.reachedDestination) {
                    thief.reachedDestination = false;

                }
                BFSFinder = new pathFinder(placesMatrix, thief);
                List<Node> currentPath = BFSFinder.pathExists();
                thief.currentPath = currentPath;

            }

            if (buildingExists(thief.getDestination())) {
                thief.getPosition();
                if (this.placesMatrix[thief.getY() / 50][thief.getX() / 50] != null) {
                    if (this.placesMatrix[thief.getY() / 50][thief.getX() / 50].getBuildingType().equals(thief.getDestination())) {

                        thief.reachedDestination = true;
                        // thief.stayThere = true;

                        System.out.println("thief is in the Security Building");

                    }
                }
            } else {
                changeDirection(thief);
            }

            //}
        });
    }

    public void movethieftotheps() {
        this.thieves.forEach(thief -> {
            //if(!thief.stayThere){

            pathFinder BFSFinder;
            if (thief.getDestination() == null || !buildingExists(thief.getDestination()) || thief.reachedDestination) {
                thief.thiefgoestopolstation();
                if (thief.reachedDestination) {
                    thief.reachedDestination = false;

                }
                BFSFinder = new pathFinder(placesMatrix, thief);
                List<Node> currentPath = BFSFinder.pathExists();
                thief.currentPath = currentPath;

            }

            if (buildingExists(thief.getDestination())) {
                thief.getPosition();
                if (this.placesMatrix[thief.getY() / 50][thief.getX() / 50] != null) {
                    if (this.placesMatrix[thief.getY() / 50][thief.getX() / 50].getBuildingType().equals(thief.getDestination())) {

                        thief.reachedDestination = true;
                        // thief.stayThere = true;

                        System.out.println("thief is in the Police Station");

                    }
                }
            } else {
                changeDirection(thief);
            }

            //}
        });
    }

    public void movecopstotheps() {
        this.cops.forEach(PoliceOfficer -> {
            //if(!thief.stayThere){

            pathFinder BFSFinder;
            if (PoliceOfficer.getDestination() == null || !buildingExists(PoliceOfficer.getDestination()) || PoliceOfficer.reachedDestination) {
                PoliceOfficer.policegoestopolstation();
                if (PoliceOfficer.reachedDestination) {
                    PoliceOfficer.reachedDestination = false;

                }
                BFSFinder = new pathFinder(placesMatrix, PoliceOfficer);
                List<Node> currentPath = BFSFinder.pathExists();
                PoliceOfficer.currentPath = currentPath;

            }

            if (buildingExists(PoliceOfficer.getDestination())) {
                PoliceOfficer.getPosition();
                if (this.placesMatrix[PoliceOfficer.getY() / 50][PoliceOfficer.getX() / 50] != null) {
                    if (this.placesMatrix[PoliceOfficer.getY() / 50][PoliceOfficer.getX() / 50].getBuildingType().equals(PoliceOfficer.getDestination())) {

                        PoliceOfficer.reachedDestination = true;
                        // thief.stayThere = true;

                        System.out.println("the Police is in the Police Station");

                    }
                }
            } else {
                changeDirection(PoliceOfficer);
            }

            //}
        });
    }

    public void movesecuritytothesb() {
        this.securities.forEach(Security -> {
            //if(!thief.stayThere){

            pathFinder BFSFinder;
            if (Security.getDestination() == null || !buildingExists(Security.getDestination()) || Security.reachedDestination) {
                // Security.seccuritygoestosb();
                Security.setDestination("SecurityBuilding");
                System.out.println("BB:" + Security.getDestination());
                if (Security.reachedDestination) {
                    Security.reachedDestination = false;

                }
                BFSFinder = new pathFinder(placesMatrix, Security);
                List<Node> currentPath = BFSFinder.pathExists();
                Security.currentPath = currentPath;

            }

            if (buildingExists(Security.getDestination())) {

                Security.getPosition();
                if (this.placesMatrix[Security.getY() / 50][Security.getX() / 50] != null) {
                    if (this.placesMatrix[Security.getY() / 50][Security.getX() / 50].getBuildingType().equals(Security.getDestination())) {

                        Security.reachedDestination = true;
                        // thief.stayThere = true;

                        System.out.println("Security is in the Security Building");

                    }
                }
            } else {
                System.out.println("AAAAAAAAAAA");
                changeDirectionofsecurity(Security);
            }

            //}
        });
    }

    public void movecopstothesb() {
        this.cops.forEach(PoliceOfficer -> {
            //if(!thief.stayThere){

            pathFinder BFSFinder;
            if (PoliceOfficer.getDestination() == null || !buildingExists(PoliceOfficer.getDestination()) || PoliceOfficer.reachedDestination) {
                PoliceOfficer.policegoestosecuritybuilding();
                if (PoliceOfficer.reachedDestination) {
                    PoliceOfficer.reachedDestination = false;

                }
                BFSFinder = new pathFinder(placesMatrix, PoliceOfficer);
                List<Node> currentPath = BFSFinder.pathExists();
                PoliceOfficer.currentPath = currentPath;

            }

            if (buildingExists(PoliceOfficer.getDestination())) {
                PoliceOfficer.getPosition();
                if (this.placesMatrix[PoliceOfficer.getY() / 50][PoliceOfficer.getX() / 50] != null) {
                    if (this.placesMatrix[PoliceOfficer.getY() / 50][PoliceOfficer.getX() / 50].getBuildingType().equals(PoliceOfficer.getDestination())) {

                        PoliceOfficer.reachedDestination = true;
                        // thief.stayThere = true;

                        System.out.println("the Police is in the Police Station");

                    }
                }
            } else {
                changeDirection(PoliceOfficer);
            }

            //}
        });
    }

    public void moveAllcops() {
        this.cops.forEach(PoliceOfficer -> {
            pathFinder BFSFinder;
            if (this.placesMatrix[PoliceOfficer.getY() / 50][PoliceOfficer.getX() / 50].getBuildingType().equals(PoliceOfficer.getDestination())) {

                PoliceOfficer.reachedDestination = true;
            }
            if (PoliceOfficer.getDestination() == null && isThiefInSecBuilding) {
                PoliceOfficer.policegoestosecuritybuilding();
                BFSFinder = new pathFinder(placesMatrix, PoliceOfficer);
                List<Node> currentPath = BFSFinder.pathExists();
                PoliceOfficer.currentPath = currentPath;
            }
            if (PoliceOfficer.reachedDestination == true) {
                PoliceOfficer.reachedDestination = false;
                atthesameplace = true;
                PoliceOfficer.policegoestopolstation();
                BFSFinder = new pathFinder(placesMatrix, PoliceOfficer);
                List<Node> currentPath = BFSFinder.pathExists();
                PoliceOfficer.currentPath = currentPath;

            }

            if (isThiefInSecBuilding) {
                PoliceOfficer.getPosition();
            } else if (atthesameplace) {
                PoliceOfficer.getPosition();
            } else {
                changeDirection(PoliceOfficer);
            }
        });
    }

    public void moveAllsecurities() {

        this.securities.forEach(Security -> {
            pathFinder BFSFinder;
            if (Security.getDestination() == null && temp) {
                Security.seccuritygoestosb();
                BFSFinder = new pathFinder(placesMatrix, Security);
                List<Node> currentPath = BFSFinder.pathExists();
                Security.currentPath = currentPath;
            }
            if (temp == true) {
                Security.getPosition();
            } else {
                changeDirection(Security);
            }
        });
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.orange);
        this.guests.forEach(guest -> {
            guest.draw(g);
            System.out.print("DRAW");
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

        //guest.draw(g);
        //guest.changeMood(5);
//          Display a grid
        for (int i = 0; i <= GAME_AREA_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, GAME_AREA_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, GAME_AREA_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if (placesMatrix[i][j] != null) {
                    BasicBuilding building = placesMatrix[i][j];
                    if (building.getSize() > 1) {
                        if (j + (building.getSize() - 1) < numberOfCols
                                && i + (building.getSize() - 1) < numberOfRows) {
                            if (building.equals(placesMatrix[i][j + (building.getSize() - 1)])
                                    && (building.equals(placesMatrix[i + (building.getSize() - 1)][j]))) {
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

    public void steal() {
        for (int i = 0; i < thieves.size(); i++) {
            for (int j = 0; j < guests.size(); j++) {
                if (guests.get(j).getX() == thieves.get(i).getX() && guests.get(j).getY() == thieves.get(i).getY()) {

                    Random rnd = new Random();
                    int randomnumber = rnd.nextInt(100) + 1;
                    if (thieves.get(i).getSkillevel() > randomnumber) {
                        System.out.println("Money is stolen");
                        tempthiefmoneyvariable = tempthiefmoneyvariable + randomnumber;
                        //isstolen=true;

                        //  catchthethief();
                    } else {
                        System.out.println("Thief is running");
                        thieves.get(i).stealMoney = true;
                        tempthiefmoneyvariable = tempthiefmoneyvariable + randomnumber;

                    }

                }

            }

        }
        // if(isstolen||tempthiefmoneyvariable>0)
        //movethieftotheden();

    }

    public void catchthethief() {
        //System.out.println(" ");
        for (int i = 0; i < thieves.size(); i++) {
            for (int j = 0; j < securities.size(); j++) {
                if (securities.get(j).getX() == thieves.get(i).getX() && securities.get(j).getY() == thieves.get(i).getY()) {
                   // if (thieves.get(i).stealMoney == true) {
                        System.out.println("Thief is caught");
                        //System.out.println(cops.get(i).reachedDestination);
                        thieves.get(i).isCaught = true;
                        thiefIsCaught=true;
                        temp = thieves.get(i).isCaught;
                    //}

                    if (thieves.get(i).getDestination() != null) {
                        isThiefInSecBuilding = true;
                        System.out.println("Police is called");
                    }
                    //}

                }

            }
        }
    }

}