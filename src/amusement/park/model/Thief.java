package amusement.park.model;
        
import java.awt.*;
import java.util.Random;


public class Thief extends Person {

  
     public static int skillevel;
    // Random  randomnumber=new Random();
    //stealmoney()
public Thief(int VOR) {
        super("thief.png");
        Random random = new Random();
        VOR =  random.nextInt(100)+1;
        this.skillevel = VOR;
        this.setX(200);
    }
//int 
 /**
     * This method is for thief to steal money from the guest.
     *
     * @param guest
     */
public void steal_money(Guest guest) {
       Random  rnd=new Random();
       int randomnumber= rnd.nextInt(100)+1;
       if(skillevel>randomnumber){
           Messagebox.infoBox("Money is stolen", "Attention");
          guest.pay(skillevel);
          guest.changeMood(skillevel);
       }
       else{
           
           guest.call_security();
           run();
       
       }
    }

    public static int getSkillevel() {
        return skillevel;
    }


 /**
     * This method is for thief to run away from the police officers.
     *
     * @param 
     */
public void run(){
Messagebox.infoBox("Thief is running back to den", "Attention");
}

 @Override
    public String getDestination(){
        return this.destination;
    }

   

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }


}



