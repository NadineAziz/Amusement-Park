/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.pathfinding;

import amusement.park.PlaceManager;
import amusement.park.model.Person;
import amusement.park.model.buildings.BasicBuilding;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author khaligov
 */
public class PathFinder {

    BasicBuilding[][] matrix;
    Person guest;
    int startX;
    int startY;
    Point startNode;
    Point endNode;
    String destination;
    boolean pathExists;
    int ex, ey;

    public PathFinder(PlaceManager placeManager, Person guest) {
        this.matrix = placeManager.getPlaces();
        this.startX = guest.getY() / 50;
        this.startY = guest.getX() / 50;
        this.startNode = new Point(startX, startY);
        this.destination = guest.getDestination();
        this.guest = guest;
        this.endNode = new Point(ex, ey);

    }

    public List<Node> pathExists() {
        boolean[][] matrixBool = new boolean[9][17];
        //List<Node> queue = new ArrayList<Node>();
        ArrayList<ArrayList<Node>> queue = new ArrayList<ArrayList<Node>>();
        ArrayList<Node> currentPath = new ArrayList<Node>();
        currentPath.add(new Node(startX, startY));
        queue.add(currentPath); // guest position will be added
        boolean pathExists = false;
        while (queue.size() > 0) {
            currentPath = queue.get(0);
            queue.remove(0);

            Node nextNode = currentPath.get(currentPath.size() - 1);
            //System.out.println("Current node in path: " + nextNode.getX() + " "+ nextNode.getY());
            matrixBool[nextNode.getX()][nextNode.getY()] = true;
            if (matrix[nextNode.getX()][nextNode.getY()] != null) {
                if (matrix[nextNode.getX()][nextNode.getY()].getBuildingType().equals(this.destination)) {
                    pathExists = true;
                    //currentPath.add(nextNode);
                    return currentPath;
                }
            }
            ArrayList<Node> neighbors = getNeighbors(nextNode, matrixBool);

            for (int i = 0; i < neighbors.size(); i++) {
                ArrayList<Node> tempPath = new ArrayList<Node>();

                for (Node p : currentPath) {
                    tempPath.add(p);
                }
                tempPath.add(neighbors.get(i));
                queue.add(tempPath);
            }
        }
        //System.out.println(pathExists ? "YES" : "NO");
        //currentPath.forEach(path -> {
        //    System.out.println("End Path "+ path.getX() + " " + path.getY()); 
        //});
        return currentPath;

    }

    public ArrayList<Node> getNeighbors(Node nextNode, boolean[][] visited) {
        int curX = nextNode.x;
        int curY = nextNode.y;

        ArrayList<Node> nextNodes = new ArrayList<Node>();
        // if can check up
        if (curX > 0) {
            Node tempPoint = new Node(curX - 1, curY);
            if (this.matrix[curX - 1][curY] != null && isValidPoint(tempPoint, visited)) {
                nextNodes.add(tempPoint);
            }
        }

        // check down
        if (curX < 8) {
            Node tempPoint = new Node(curX + 1, curY);
            if (this.matrix[curX + 1][curY] != null && isValidPoint(tempPoint, visited)) {
                nextNodes.add(tempPoint);
            }
        }

        // check left
        if (curY > 0) {
            Node tempPoint = new Node(curX, curY - 1);
            if (this.matrix[curX][curY - 1] != null && isValidPoint(tempPoint, visited)) {
                nextNodes.add(tempPoint);
            }
        }

        // check right
        if (curY < 16) {
            Node tempPoint = new Node(curX, curY + 1);
            if (this.matrix[curX][curY + 1] != null && isValidPoint(tempPoint, visited)) {
                nextNodes.add(tempPoint);
            }
        }
        return nextNodes;
    }

    public boolean isValidPoint(Node curr, boolean[][] matrixBool) {
        int x = curr.getX();
        int y = curr.getY();
        if ((x >= 0 && x < 9) && (y >= 0 && y < 17) && this.matrix[x][y] != null && !matrixBool[x][y]) {
            if (this.matrix[x][y].getBuildingType().equals(destination) || this.matrix[x][y].getBuildingType().equals("Path") || this.matrix[x][y].getBuildingType().equals("ThiefDen")) {
                return true;
            }
        }
        return false;
    }

}
