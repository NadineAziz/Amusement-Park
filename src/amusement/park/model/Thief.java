package amusement.park.model;

import java.util.Random;

public class Thief extends Person {

    public static int skillevel;
    public static int mny = 0;
    public boolean stealMoney = false;
    public boolean stayThere = false;
    public boolean isinthesb = false;
    public boolean isCaught = false;
    // Random  randomnumber=new Random();
    //stealmoney()

    public Thief(int VOR) {
        super("thief.png");
        Random random = new Random();
        VOR = random.nextInt(100) + 1;
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
        Random rnd = new Random();
        int randomnumber = rnd.nextInt(100) + 1;
        if (skillevel > randomnumber) {
            Messagebox.infoBox("Money is stolen", "Attention");
            guest.pay(skillevel);
            guest.changeMood(skillevel);
        } else {

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
    public void run() {
        this.destination = "ThiefDen";

//Messagebox.infoBox("Thief is running back to den", "Attention");
    }

    public void thiefgoestosecbuilding() {
        this.destination = "SecurityBuilding";

//Messagebox.infoBox("Thief is running back to den", "Attention");
    }

    public void thiefgoestopolstation() {
        this.destination = "PoliceStation";

//Messagebox.infoBox("Thief is running back to den", "Attention");
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setMny(int mny) {
        this.mny = mny;
    }

    public int getMny() {
        return mny;
    }

}
