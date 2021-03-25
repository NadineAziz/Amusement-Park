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


public void steal_money(Guest guest) {
       Random  rnd=new Random();
       int randomnumber= rnd.nextInt(100);
       if(skillevel>randomnumber){
          guest.pay(skillevel);
       }
       else{
           guest.call_security();
           run();
       
       }
    }

public void run(){}
}



