/*
 * Authors Brayden Clark and Tanner Bearden
 * Date: 4/4/2024
 * 
 * Collaborators: None
 * 
 * 
 * This class is used to setup and display game information (cards)
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
    
    
    private static GameInformationPanel theInstance = new GameInformationPanel();
    
    /**
     * Constructor for the GameInformationPanel
     */
    private GameInformationPanel() {
        super();
        initializePanels();
    }

    public static GameInformationPanel getInstance() {
        return theInstance;
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
        ArrayList<Card> seenCardsArrayList = new ArrayList<Card>(seenCards); // put set of seen cards into an array list
        
        // get player color using decode function
        //Color playerColor = Color.decode(player.getColor());

        // add in hand label to panel and loop through in hand cards
        panel.add(new JLabel("In Hand: "));
        cardLoop(cards, panel, type, inHand, player);

        // add seen label to panel and loop through seen cards
        panel.add(new JLabel("Seen: "));
        cardLoop(seenCardsArrayList, panel, type, isSeen, player);
    
        // add panel to parent
        add(panel);

        revalidate();
        repaint();
    }

    /**
     * Loops through the cards and adds them to the panel
     * @param cards
     * @param panel
     * @param type
     * @param isSeen
     * @param playerColor
     */
    private void cardLoop(ArrayList<Card> cards, JPanel panel, CardType type, boolean isSeen, Player player) {

        Board board = Board.getInstance();

        for (Card card : cards) {
            if (card.getCardType() == type) {
                JTextField textField = new JTextField(card.getCardName());
                textField.setEditable(false);

                // set the background color of the text field to the player's color
                for (Player p : board.getPlayers()) {
                    if (p.getCards().contains(card)) {
                        textField.setBackground(Color.decode(p.getColor()));
                    }
                }


                panel.add(textField);
                isSeen = true;
            }
        }

        if (!isSeen) {
            JTextField noneInHandField = new JTextField("None");
            noneInHandField.setEditable(false);
            panel.add(noneInHandField);
        }
    }

    /**
     * Updates the panels with the player's cards
     * @param player
     */
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
