package amusement.park.model;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class Person {
    private ImageIcon icon;

    public Person(String pictureName) {
        try {
            icon = new ImageIcon(Paths.get("images/person/" + pictureName).toUri().toURL());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.INFO, "AAAAA", ex);
        }
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
