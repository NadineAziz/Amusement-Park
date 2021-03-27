package amusement.park;

import amusement.park.model.Guest;
import amusement.park.model.buildings.games.*;
import amusement.park.model.buildings.gardens.*;
import amusement.park.model.buildings.restaurants.CornDogStand;
import amusement.park.ui.BuildingItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static amusement.park.GameGUI.SCREEN_SIZE;
import amusement.park.model.buildings.Path;

public class GamePanel extends JPanel {
    private BuildingItem selectedItem;
    private final int numberOfQuests = 1;
    private final List<Guest> guests = new ArrayList<>();
    private int fps;
    private CoinsPanel coinsPanel;

    public GamePanel(int fps, CoinsPanel coinsPanel) {
        super();
        this.fps = fps;
        this.coinsPanel = coinsPanel;

        for (int i = 0; i < numberOfQuests; i++) {
            guests.add(new Guest(100));
        }

        GameArea gameArea = new GameArea(this);
        gameArea.setBackground(new Color(0x76909F));

        setLayout(new BorderLayout());
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.LIGHT_GRAY);
        itemsPanel.setPreferredSize(new Dimension(SCREEN_SIZE.width,130));
        itemsPanel.setLayout(new FlowLayout());
        itemsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        BuildingItem buildingItem1 = new BuildingItem(new FirstGame());
        buildingItem1.addActionListener(createItemClickedListener());
        BuildingItem buildingItem2 = new BuildingItem(new SecondGame());
        buildingItem2.addActionListener(createItemClickedListener());
        BuildingItem buildingItem3 = new BuildingItem(new ThirdGame());
        buildingItem3.addActionListener(createItemClickedListener());
        BuildingItem buildingItem4 = new BuildingItem(new CornDogStand());
        buildingItem4.addActionListener(createItemClickedListener());
        BuildingItem buildingItem5 = new BuildingItem(new Path(0,0));
        buildingItem5.addActionListener(createItemClickedListener());
        BuildingItem buildingItem6 = new BuildingItem(new Grass());
        buildingItem6.addActionListener(createItemClickedListener());
        BuildingItem buildingItem7 = new BuildingItem(new Tree());
        buildingItem7.addActionListener(createItemClickedListener());
        BuildingItem buildingItem8 = new BuildingItem(new Shrub());
        buildingItem8.addActionListener(createItemClickedListener());

        itemsPanel.add(buildingItem1);
        itemsPanel.add(buildingItem2);
        itemsPanel.add(buildingItem3);
        itemsPanel.add(buildingItem4);
        itemsPanel.add(buildingItem5);
        itemsPanel.add(buildingItem6);
        itemsPanel.add(buildingItem7);
        itemsPanel.add(buildingItem8);

        JPanel jPanel1 = new JPanel();
        jPanel1.setPreferredSize(new Dimension(200, SCREEN_SIZE.height));
        jPanel1.setBackground(new Color(0x76909F));
        JPanel jPanel2 = new JPanel();
        jPanel2.setPreferredSize(new Dimension(200, SCREEN_SIZE.height));
        jPanel2.setBackground(new Color(0x76909F));

        add(BorderLayout.EAST, jPanel1);
        add(BorderLayout.WEST, jPanel2);
        add(BorderLayout.CENTER, gameArea);
        add(BorderLayout.SOUTH, itemsPanel);
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public int getFps() {
        return fps;
    }

    private ActionListener createItemClickedListener() {
        return e -> selectedItem = (BuildingItem) e.getSource();
    }

    public BuildingItem getSelectedItem() {
        return selectedItem;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
