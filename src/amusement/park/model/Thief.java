package amusement.park.model;
        
import java.awt.*;
import java.util.Random;


public class Thief extends Person {
     private final int skillevel;
    // Random  randomnumber=new Random();
    //stealmoney()
public Thief(int skillevel) {
        super("thief.png");
        Random random = new Random();
        skillevel =  random.nextInt(100);
        this.skillevel = skillevel;
    }
//int 
 /**
     * This method is for thief to steal money from the guest.
     *
     * @param guest
     */
public void steal_money(Guest guest) {
       Random  rnd=new Random();
       int randomnumber= rnd.nextInt(100);
       if(skillevel>randomnumber){
          guest.pay(skillevel);
          guest.changeMood(skillevel);
       }
       else{
           guest.call_security();
           run();
       
       }
    }
 /**
     * This method is for thief to run away from the police officers.
     *
     * @param 
     */
public void run(){}
}



