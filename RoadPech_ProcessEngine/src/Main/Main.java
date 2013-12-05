/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import UI.Main.WelcomeScreen;
import javax.swing.UIManager;

/**
 *
 * @author Rashmika
 */
public class Main {
    public static void main(String[] args) {
        try {
            /**
             * Set the Nimbus user interface theme
             */
             for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                   if ("Nimbus".equals(info.getName())) {
                       UIManager.setLookAndFeel(info.getClassName());
                       break;
                  }
            }
        } catch (Exception e) {
            System.out.println("Error loading Nimbus. Using the default theme.");
        }

        new WelcomeScreen().setVisible(true);
    }
}
