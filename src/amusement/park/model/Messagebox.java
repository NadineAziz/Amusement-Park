/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park.model;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Messagebox {
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    //ClassNameHere.infoBox("YOUR INFORMATION HERE", "TITLE BAR MESSAGE");
}
