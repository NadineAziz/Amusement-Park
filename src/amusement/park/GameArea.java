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
import amusement.park.model.Security;
import amusement.park.model.SecurityBuilding;
import amusement.park.model.Thief;

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
    private int numberOfThieves=0;
    private int numofcops=0;
    private int numofsecurities=0;
    private final int Entrancemoney=30;
    public final BasicBuilding[][] placesMatrix;
    public final int numberOfRows = GAME_AREA_HEIGHT / UNIT_SIZE;
    public final int numberOfCols = GAME_AREA_WIDTH / UNIT_SIZE;
    private boolean parkOpen = false;
    private final List<Guest> guests = new ArrayList<>();
    private final List<Thief> thieves = new ArrayList<>();
    private final List<PoliceOfficer> cops = new ArrayList<>();
    private final List<Security> securities = new ArrayList<>();
    Clicklistener click = new Clicklistener();
    JButton startButton;
    
    private GamePanel gpanel;
    
    public GameArea(GamePanel gamePanel) {
        // gamePanel.payentrancefee(getNumberOfQuests()*Entrancemoney); //bunu harasa qoymaliyam ki pul cixsin
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

    public int getNumberOfQuests() {
        return numberOfQuests;
    }

    public int getNumofcops() {
        return numofcops;
    }

    public void setNumofcops(int num){
        this.numofcops = num;
        for (int i = 0; i < numofcops; i++) {
            cops.add(new PoliceOfficer());
        }
    }
    
    
    public void setNumOfGuests(int num){
        this.numberOfQuests = num;
        for (int i = 0; i < numberOfQuests; i++) {
            guests.add(new Guest(random.nextInt(10000)+1));
        }
    }
      public void setNumOfThieves(int num){
        this.numberOfThieves = num;
        for (int i = 0; i < numberOfThieves; i++) {
            thieves.add(new Thief(Thief.skillevel));
        }
    }
       public void setNumOfsecurities(int num){
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
    
    private boolean buildingExists(String buildingType){
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfCols; ++j) {
                if(placesMatrix[i][j]!= null){
                    if(placesMatrix[i][j].getBuildingType().equals(buildingType)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    private class Clicklistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!parkOpen){
                setNumOfGuests(3);
                parkOpen = true;  
                setNumOfThieves(2);
                setNumofcops(1);
                setNumOfsecurities(1);
               // gamePanel.buyBuilding();
                gpanel.payentrancefee(getNumberOfQuests()*Entrancemoney);
                //CoinsPanel.increaseCoins(90);
                
               //CoinsPanel.moneyy=CoinsPanel.moneyy+Entrancemoney*getNumberOfQuests();
               //CoinsPanel.getCoinsField().setText((actualValue + value) + "");
               
             
                
                System.out.println("Button is clicked");
                moveAllGuests();
                moveAllcops();
                parkOpen = true;
            }
        }
    }

    /**
     * Displays the police station, cave and atm to random empty places
     */
    private void placeRandomBuildings() {
        BasicBuilding policeStation = new PoliceStation();
        BasicBuilding cave = new ThiefDen();
        BasicBuilding security = new SecurityBuilding();
        //BasicBuilding atm = new ATM();
        int indexX = 0;
        int indexY = 0;
        addBuilding(policeStation, indexX, indexY);
        addBuilding(security,indexX,indexY+15);

        addBuilding(cave, indexX+5, indexY);
        //tryPlacingBuilding(atm, indexX, indexY);

    }

    private void tryPlacingBuilding(BasicBuilding building, int indexX, int indexY) {
        while (!addBuilding(building, indexX, indexY)) {
            indexX = random.nextInt(numberOfRows - 1);
            indexY = random.nextInt(numberOfCols - 1);
        }
    }

    /**
     * guest moves only in path
     */
    public boolean guestMoveInPath(int nextX, int nextY){
        int row = (nextY/50);
        int column = (nextX/50);
        if (placesMatrix[row][column]!= null){
            if(placesMatrix[row][column].getBuildingType().equals("Path")){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Moves the guests in the matrix
     */
    
    
    public void changeDirection(Guest guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir==Direction.UP){
            if(guest.getY()>0){
                if(guestMoveInPath(guest.getX(),(guest.getY()-50))){
                    guest.move(0, -50); 
                }
            }
        }else if (dir==Direction.DOWN){
            if(guest.getY()<400){
                if(guestMoveInPath(guest.getX(),guest.getY()+50)){
                    guest.move(0, 50); 
                }
            }
        }else if (dir==Direction.LEFT){
            if(guest.getX()>0){
                if(guestMoveInPath((guest.getX()-50),guest.getY())){
                    guest.move(-50, 0); 
                }
            }
        }else if (dir==Direction.RIGHT){
            if(guest.getX()<800){
                if(guestMoveInPath(guest.getX()+50,guest.getY())){
                    guest.move(50, 0); 
                }
            }
        }
    }
   public void changeDirection2(PoliceOfficer guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir==Direction.UP){
            if(guest.getY()>0){
                if(guestMoveInPath(guest.getX(),(guest.getY()-50))){
                    guest.move(0, -50); 
                }
            }
        }else if (dir==Direction.DOWN){
            if(guest.getY()<400){
                if(guestMoveInPath(guest.getX(),guest.getY()+50)){
                    guest.move(0, 50); 
                }
            }
        }else if (dir==Direction.LEFT){
            if(guest.getX()>0){
                if(guestMoveInPath((guest.getX()-50),guest.getY())){
                    guest.move(-50, 0); 
                }
            }
        }else if (dir==Direction.RIGHT){
            if(guest.getX()<800){
                System.out.println();
                if(guestMoveInPath(guest.getX()+50,guest.getY())){
                    guest.move(50, 0); 
                }
            }
        }
    }
    
     public void changeDirectionofthief(Thief guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir==Direction.UP){
            if(guest.getY()>0){
                if(guestMoveInPath(guest.getX(),(guest.getY()-50))){
                    guest.move(0, -50); 
                }
            }
        }else if (dir==Direction.DOWN){
            if(guest.getY()<400){
                if(guestMoveInPath(guest.getX(),guest.getY()+50)){
                    guest.move(0, 50); 
                }
            }
        }else if (dir==Direction.LEFT){
            if(guest.getX()>0){
                if(guestMoveInPath((guest.getX()-50),guest.getY())){
                    guest.move(-50, 0); 
                }
            }
        }else if (dir==Direction.RIGHT){
            if(guest.getX()<800){
                System.out.println();
                if(guestMoveInPath(guest.getX()+50,guest.getY())){
                    guest.move(50, 0); 
                }
            }
        }
    }
      public void changeDirectionofsecurity(Security guest) {
        Direction dir = Direction.values()[random.nextInt(4)];
        if (dir==Direction.UP){
            if(guest.getY()>0){
                if(guestMoveInPath(guest.getX(),(guest.getY()-10))){
                    guest.move(0, -10); 
                }
            }
        }else if (dir==Direction.DOWN){
            if(guest.getY()<400){
                if(guestMoveInPath(guest.getX(),guest.getY()+10)){
                    guest.move(0, 10); 
                }
            }
        }else if (dir==Direction.LEFT){
            if(guest.getX()>0){
                if(guestMoveInPath((guest.getX()-10),guest.getY())){
                    guest.move(-10, 0); 
                }
            }
        }else if (dir==Direction.RIGHT){
            if(guest.getX()<800){
                System.out.println();
                if(guestMoveInPath(guest.getX()+10,guest.getY())){
                    guest.move(10, 0); 
                }
            }
        }
    }
    
    
    public void moveAllGuests() {
        this.guests.forEach(guest -> {
             pathFinder BFSFinder;
             steal();
             
            //if guest has destination, move to the destination
            if(guest.getDestination() == null || !buildingExists(guest.getDestination()) || guest.reachedDestination){
                guest.generateDestination();
                //System.out.println(guest.getDestination());
                if(guest.reachedDestination){
                    guest.reachedDestination = false;
                }
                BFSFinder = new pathFinder(placesMatrix,guest);
                List<Node> currentPath= BFSFinder.pathExists();
                guest.currentPath = currentPath; 
            }
            if (buildingExists(guest.getDestination())){
                System.out.println("BFS movements");
                guest.getPosition();
                if(this.placesMatrix[guest.getY()/50][guest.getX()/50].getBuildingType().equals(guest.getDestination())){
                    guest.reachedDestination = true;
                    if(this.placesMatrix[guest.getY()/50][guest.getX()/50].getBuildingType().equals("ATM")){
                String value = JOptionPane.showInputDialog(
                        GameArea.this,
                        "Amount of money:",
                        0
                );
                guest.pay(-Integer.valueOf(value));
                        System.out.println("Cash withdrawal is done!");
                    }
                    System.out.println("Yayy guest reached destination");
                    guest.pay(10);
                    guest.changeMood(-10);
                    if(guest.getMood()<=0){
                    System.out.println("Mood tanked");
                    //guest.setDestination("HotDogStand");
                    }
                   // guest.setDestination("HotDogStand");
                }
            }else{
                System.out.println("randomly moving");
                changeDirection(guest);
            }
        });
     }
    public void moveAllThieves() {
        this.thieves.forEach(thief -> {
            //if guest has destination, move to the destination
            
            //else move randomly on path
            changeDirectionofthief(thief);
        });
     }
     public void moveAllcops() {
        this.cops.forEach(PoliceOfficer -> {
            //if guest has destination, move to the destination
            
            //else move randomly on path
            changeDirection2(PoliceOfficer);
        });
     }
      public void moveAllsecurities() {
        this.securities.forEach(Security -> {
            //if guest has destination, move to the destination
            
            //else move randomly on path
            changeDirectionofsecurity(Security);
        });
     }
    

     class NewFrameListener implements ActionListener {

         @Override
         public void actionPerformed(ActionEvent e) {
             moveAllGuests();
             moveAllThieves();
             moveAllcops();
             moveAllsecurities();
             repaint();
         }
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
                        if (j + (building.getSize() - 1) < numberOfCols &&
                            i + (building.getSize() - 1) < numberOfRows) {
                            if (building.equals(placesMatrix[i][j + (building.getSize() - 1)]) &&
                                    (building.equals(placesMatrix[i + (building.getSize() - 1)][j]))) {
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
        System.out.println(" ");
        for(int i =0;i<thieves.size();i++){
        for (int j=0;j<guests.size();j++){
            if(placesMatrix[i][j].getBuildingType().equals("Path")){
                if(guests.get(j).getX()==thieves.get(i).getX()&&guests.get(j).getY()==thieves.get(i).getY()){
        
       Random  rnd=new Random();
       int randomnumber= rnd.nextInt(100)+1;
       if(thieves.get(i).getSkillevel()>randomnumber){
          Messagebox.infoBox("Money is stolen", "Attention");
          guests.get(i).pay(Thief.getSkillevel());
          guests.get(i).changeMood(Thief.getSkillevel());
       }
       else{
           
           guests.get(i).call_security();
           
       
       }
              }
       }
         
        }
        }
    }

}
