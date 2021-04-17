/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model.buildings;

/**
 *
 * @author User
 */
public class Path extends Building{
private  final int tileCost;
private boolean hasTrash=false;
private int trashCount;

    public Path(int tileCost, int trashCount) {
        super("path3.png", 5, 10, 10,"Path");
        this.tileCost = tileCost;
        this.trashCount = trashCount;
    }
  /**
     * This method is going to build the path for the  guests
     * and decrease their money
     *
     * @param numberoftiles
     */
    public void buildPath(int numberoftiles){

    }
     /**
     * This method is going to check whether path has trashcan or not
     *
     * @param 
     */
    public boolean hasTrashCan(){return false;}
    public int countTrashs(){
        int cnt=0;
    if(hasTrashCan()){
        cnt++;
    }
    return cnt;
    }
    }
    

