import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater( () -> {;
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);
        });
    }
}
