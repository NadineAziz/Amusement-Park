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
        super("tile.jpg", 5, 10, 10);
        this.tileCost = tileCost;
        this.trashCount = trashCount;
    }
 
    public void buildPath(int numberoftiles){
    
    }
    public boolean hasTrashCan(){return false;}
    public int countTrashs(){
        int cnt=0;
    if(hasTrashCan()){
        cnt++;
    }
    return cnt;
    }
    }
    

