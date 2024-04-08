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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;


public class GameInformationPanel extends JPanel {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 600;


    // create room, people, and weapon panels
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
        setSize(WIDTH, HEIGHT);

        // Set border titles
        setBorder(BorderFactory.createTitledBorder("Known Cards"));
        peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
        weaponPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));

        // add panels to parent
        add(peoplePanel);
        add(roomPanel);
        add(weaponPanel);


        repaint();

    }

    /**
     * Updates the panel with the player's cards
     * @param panel panel to be updated
     * @param player player to get cards from
     * @param type type of card to get
     */
    public void updatePanel(JPanel panel, Player player, CardType type) {

        // clear the panel
        panel.removeAll();
        
        boolean inHand = false;
        boolean isSeen = false;

        ArrayList<Card> cards = player.getCards();
        Set<Card> seenCards = player.getSeenCards();
        
        // get player color using decode function
        Color playerColor = Color.decode(player.getColor());

        // add labels to panel
        panel.add(new JLabel("In Hand: "));
    
        // loop through cards in hand
        for (Card card : cards) {

            if (card.getCardType() == type) {
                JTextField textField = new JTextField(card.getCardName());
                textField.setEditable(false);
                textField.setBackground(playerColor);
                panel.add(textField);
                inHand = true;
            }
        }

        // draw 'None' textField if there are no cards of 'type' in hand
        if (!inHand) {
            JTextField noneInHandField = new JTextField("None");
            noneInHandField.setEditable(false);
            panel.add(noneInHandField);
        }

        // add seen label
        panel.add(new JLabel("Seen: "));

    
        // loop through seen cards
        for (Card card : seenCards) {
            if (card.getCardType() == type) {
                JTextField textField = new JTextField(card.getCardName());
                textField.setEditable(false);
                textField.setBackground(Color.decode(card.getColor()));
                panel.add(textField);
                isSeen = true;
            }
        }

        // draw 'None' textField if there are no cards of 'type' in hand
        if (!isSeen) {
            JTextField noneInHandField = new JTextField("None");
            noneInHandField.setEditable(false);
            panel.add(noneInHandField);
        }

        
    
        // add panel to parent
        add(panel);

        revalidate();
        repaint();
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
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Test filling in the data
        Board board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        board.deal();


        // create test solution
        Solution oneMatchingSolution = new Solution(new Card("Kitchen", CardType.ROOM), new Card("Eliza", CardType.PERSON), new Card("Rock", CardType.WEAPON));
        
        // handle suggestion to create seen cards
        board.handleSuggestion(board.getPlayers().get(0), oneMatchingSolution, board.getPlayers());
        panel.updatePanels(board.getPlayers().get(0));

        
    }

}
