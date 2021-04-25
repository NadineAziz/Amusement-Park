package amusement.park.model;


public class PoliceOfficer extends Person {
    public PoliceOfficer() {
        super("policeman.png");
    }
    
public void policegoestosecuritybuilding(){
    this.destination="SecurityBuilding";
  
//Messagebox.infoBox("Thief is running back to den", "Attention");
}
public void policegoestopolstation(){
    this.destination="PoliceStation";
  
//Messagebox.infoBox("Thief is running back to den", "Attention");
}
@Override
    public void setDestination(String destination) {
        this.destination = destination;
    }
@Override
    public String getDestination(){
        return this.destination;
    }
}
