/*
 * Authors Brayden Clark and Tanner Bearden
 * Date: 4/4/2024
 * 
 * Collaborators: None
 * 
 * 
 * This class is used to setup and display buttons and game information
 */
package clueGame;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameInformationPanel extends JPanel {


    JPanel peoplePanel;
    JPanel roomPanel;
    JPanel weaponPanel;
    
    
    
    public GameInformationPanel() {
        super();
        initializePanels();
        //updatePanels();
    }

    /**
     * Initializes the panels for the game control panel
     */
    private void initializePanels() {
        setLayout(new GridLayout(1,1));



        add(peoplePanel);
        add(roomPanel);
        add(weaponPanel);

    }

    private void updatePanel(JPanel panel, CardType card) {
        
        // rebuid contents and add it pack to parent
        

        
    }

    private void updatePanels() {
        // update the known cards
        updatePanel(peoplePanel, CardType.PERSON);
        updatePanel(roomPanel, CardType.ROOM);
        updatePanel(weaponPanel, CardType.WEAPON);
    }




    public static void main(String[] args) {
        GameInformationPanel panel = new GameInformationPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(160, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Test filling in the data
    }

}
