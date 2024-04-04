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
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.util.ArrayList;

public class GameInformationPanel extends JPanel {


    JPanel peoplePanel = new JPanel(new GridLayout(0, 1));
    JPanel roomPanel = new JPanel(new GridLayout(0,1));
    JPanel weaponPanel = new JPanel(new GridLayout(0,1));
    
    
    
    public GameInformationPanel() {
        super();
        initializePanels();
    }

    /**
     * Initializes the panels for the game control panel
     */
    private void initializePanels() {
        
        // set layout for the panel
        setLayout(new GridLayout(3, 1));

        // Set border titles
        setBorder(BorderFactory.createTitledBorder("Known Cards"));
        peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
        weaponPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));

        add(peoplePanel);
        add(roomPanel);
        add(weaponPanel);

    }

    private void updatePanel(JPanel panel, Player player, CardType type) {
        panel.removeAll();

        ArrayList<Card> cards = player.getCards();
        Set<Card> seenCards = player.getSeenCards();

        // rebuid contents and add it pack to parent
        panel.add(new JLabel("In Hand: "));

        // loop through cards in hand
        for (Card card : cards) {
            if (card.getCardType() == type) {
                JTextField textField = new JTextField(card.getCardName());
                textField.setEditable(false);
                panel.add(textField);
            }
        }

        panel.add(new JLabel("Seen: "));

        // loop through seen cards
        for (Card card : seenCards) {
            if (card.getCardType() == type) {
                JTextField textField = new JTextField(card.getCardName());
                textField.setEditable(false);
                panel.add(textField);
            }
        }
        
        
        add(panel);
    }

    public void updatePanels(Player player) {

        // update the known cards
        updatePanel(peoplePanel, player, CardType.PERSON);
        updatePanel(roomPanel, player, CardType.ROOM);
        updatePanel(weaponPanel, player, CardType.WEAPON);
    }




    public static void main(String[] args) {
        GameInformationPanel panel = new GameInformationPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(160, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Test filling in the data
        Board board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        board.deal();

        Player testPlayer = board.getPlayers().get(0);
        
        panel.updatePanels(testPlayer);
    }

}
