/*
 * Authors Brayden Clark and Tanner Bearden
 * Date: 4/2/2024
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
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import java.util.ArrayList;



public class GameControlPanel extends JPanel {

    Board board = Board.getInstance();

    private boolean noMatchingCards;
    private int dieRoll;
    private String guess;
    private String result;
    private String turn;

    JTextField guessTextField;
    JTextField guessResultTextField;
    JTextField turnTextField;
    JTextField rollTextField;
    JPanel guessPanel;
    JPanel guessResultPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel buttonPanel;
    JPanel turnPanel;
    JPanel rollPanel;
    JLabel turnLabel;
    JLabel rollLabel;
    JButton nextPlayer;
    JButton makeAccusation;
  
    private static GameControlPanel theInstance = new GameControlPanel();

    private GameControlPanel() {
        super();
        initializePanels();
    }

    public static GameControlPanel getInstance() {
        return theInstance;
    }

    /**
     * Initializes the panels for the game control panel
     */
    private void initializePanels() {
        setLayout(new GridLayout(1, 2));

        // Define text fields, panels, labels, abd buttons
        guessTextField = new JTextField();
        guessResultTextField = new JTextField();
        turnTextField = new JTextField();
        rollTextField = new JTextField();
        guessPanel = new JPanel();
        guessResultPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        buttonPanel = new JPanel();
        turnPanel = new JPanel();
        rollPanel = new JPanel();
        turnLabel = new JLabel("Whose Turn: ");
        rollLabel = new JLabel("Roll: ");
        nextPlayer = new JButton("Next Player");
        makeAccusation = new JButton("Make an Accusation");
        


        // left panel setup
        leftPanel.setLayout(new GridLayout(2, 1));
        guessPanel.setLayout(new GridLayout(1, 1));
        guessPanel.setBorder(BorderFactory.createTitledBorder("Guess: "));
        guessTextField.setEditable(false);
        guessPanel.add(guessTextField);

        guessResultPanel.setLayout(new GridLayout(1, 1));
        guessResultPanel.setBorder(BorderFactory.createTitledBorder("Guess Result: "));
        guessResultTextField.setEditable(false);
        guessResultPanel.add(guessResultTextField);

        leftPanel.add(guessPanel);
        leftPanel.add(guessResultPanel);


        // right panel setup
        rightPanel.setLayout(new GridLayout(3, 1));
        turnPanel.setLayout(new GridLayout(1, 2));
        turnTextField.setEditable(false);
        turnPanel.add(turnLabel);
        turnPanel.add(turnTextField);

        rollPanel.setLayout(new GridLayout(1, 2));
        rollTextField.setEditable(false);
        rollPanel.add(rollLabel);
        rollPanel.add(rollTextField);

        // button panel setup
        buttonPanel.setLayout(new GridLayout(1, 2));

        nextPlayer.addActionListener(nextPlayerListener);
        makeAccusation.addActionListener(makeAccusationListener);
        buttonPanel.add(nextPlayer);
        buttonPanel.add(makeAccusation);

        buttonPanel.add(makeAccusation);

        rightPanel.add(turnPanel);
        rightPanel.add(rollPanel);
        rightPanel.add(buttonPanel);

        // add left and right to parent
        add(leftPanel);
        add(rightPanel);
    }

    protected ActionListener nextPlayerListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            boolean disproveFlag = false;

            Player currentPlayer = board.getCurrentPlayer();

            guessResultTextField.setText("");
            guessTextField.setText("");

            guessResultTextField.setBackground(Color.WHITE);
            guessTextField.setBackground(Color.WHITE);
            
            // if turn is not over
            if (!board.isTurnOver()) {
                JOptionPane.showMessageDialog(null, "You must finish your turn before moving to the next player.");
                return;
            }
            // if turn is over
            else { 
                board.nextPlayer();
                board.setIsMoved(false);
                currentPlayer = board.getCurrentPlayer();

                // roll dice
                int roll = currentPlayer.rollDie();
                
                // calc targets
                board.calcTargets(board.getCell(currentPlayer.getRow(), currentPlayer.getCol()), roll);

                // update game control panel
                Color playerColor = Color.decode(currentPlayer.getColor());
                setTurn(currentPlayer, roll, playerColor);

                // is new player hman
                if (currentPlayer instanceof HumanPlayer) {
                    // display targets
                    board.setTargetsVisible(true);

                    // flag unfinished turn
                    board.setTurnOver(false);

                }
                else {
                    
                    // do move (select random target)
                    BoardCell cell = board.selectTarget();

                    if (cell == null) {
                        cell = board.getCell(currentPlayer.getRow(), currentPlayer.getCol());
                    }

                    // moving logic
                    BoardCell temp = board.getCell(currentPlayer.getRow(), currentPlayer.getCol());
                    temp.setOccupied(false);
                    currentPlayer.doMove(cell.getRow(), cell.getCol(), 0);
                    cell.setOccupied(true);



                    if (cell.isRoom()) {
                        // create a suggestion
                        ComputerPlayer computer = (ComputerPlayer) currentPlayer;
                        Solution suggestion = computer.createSuggestion(currentPlayer, board);

                        // guess panel
                        guessTextField.setText(suggestion.toString());
                        guessTextField.setBackground(Color.decode(currentPlayer.getColor()));
                        
                        // call handle suggestion
                        Card disputingCard = board.handleSuggestion(currentPlayer, suggestion, board.getPlayers());

                        if (disputingCard != null) {
                            for (Player player : board.getPlayers()) {
                                if (player.getCards().contains(disputingCard)) {
                                    guessResultTextField.setBackground(Color.decode((player.getColor())));
                                }
                            }

                            if (currentPlayer instanceof ComputerPlayer) {
                                // update result panel
                                guessResultTextField.setText("Suggestion Disproven!");
                                
                            }
                            else {
                                // update result panel
                                guessResultTextField.setText(disputingCard.getCardName());
                            }
                            disproveFlag = true;
                        }
                        else {
                            disproveFlag = false;
                        }
                        
                        // set flag if no one can disprove
                        for (Card card : currentPlayer.getCards()) {
                            if (suggestion.getPerson().equals(card) || suggestion.getRoom().equals(card) || suggestion.getWeapon().equals(card)) {
                                noMatchingCards = false;
                                break;
                            }
                            else {
                                noMatchingCards = true;
                            }
                        }

                        if (disproveFlag == false && noMatchingCards == true) {
                            if (board.checkAccusation(suggestion)) {
                                JOptionPane.showMessageDialog(null, "<html><div style='text-align: center;'>Accusation was correct! " + currentPlayer.getName() + " loses!" + "\nThe solution was: " + suggestion.toString());
                            }
                            // close all windows
                            System.exit(0);
                        }

                        

                    }
                }
            }
            
            
            board.repaint();
        }

    };

    /**
     * Make accusation listener
     */
    protected ActionListener makeAccusationListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // get current player
            Player currentPlayer = board.getCurrentPlayer();

            // if player is human
            if (currentPlayer instanceof HumanPlayer) {

                // get list of room names
                ArrayList<String> roomNames = new ArrayList<String>();
                for (Card card : board.getRooms()) {
                    if (card.getCardName().equals("Walkway") || card.getCardName().equals("Unused")) {
                        continue;
                    }
                    roomNames.add(card.getCardName());
                }
                
                // get list of player names
                ArrayList<String> playerNames = new ArrayList<String>();
                for (Player player : board.getPlayers()) {
                    playerNames.add(player.getName());
                }

                // get list of weapon names
                ArrayList<String> weaponNames = new ArrayList<String>();
                for (Card card : board.getWeapons()) {
                    weaponNames.add(card.getCardName());
                }
                

                // create dialog box java
                JDialog suggestionDialog = new JDialog();
                suggestionDialog.setLocationRelativeTo(null);
                suggestionDialog.setLayout(new GridLayout(1, 2));
                suggestionDialog.setSize(300, 200);
                suggestionDialog.setVisible(true);
                suggestionDialog.setTitle("Make a suggestion");


                // add labels
                JLabel roomLabel = new JLabel("Room");
                JLabel playerLabel = new JLabel("Person");
                JLabel weaponLabel = new JLabel("Weapon");

                // add drop down to select room
                JComboBox<String> roomComboBox = new JComboBox<String>(roomNames.toArray(new String[roomNames.size()]));

                // add drop down to select player
                JComboBox<String> playerComboBox = new JComboBox<String>(playerNames.toArray(new String[playerNames.size()]));

                // add drop down to select weapon
                JComboBox<String> weaponComboBox = new JComboBox<String>(weaponNames.toArray(new String[weaponNames.size()]));

                

                // add labels and submit button on left column
                JPanel leftPanel = new JPanel();
                leftPanel.setLayout(new GridLayout(4, 1));
                leftPanel.add(roomLabel);
                leftPanel.add(playerLabel);
                leftPanel.add(weaponLabel);
                // submit button
                JButton submitButton = new JButton("Submit");
                leftPanel.add(submitButton);

                // add textfield, dropdowns, and cancel button to right
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new GridLayout(4, 1));
                rightPanel.add(roomComboBox);
                rightPanel.add(playerComboBox);
                rightPanel.add(weaponComboBox);
                // cancel button
                JButton cancelButton = new JButton("Cancel");
                rightPanel.add(cancelButton);

                // add panels to dialog
                suggestionDialog.add(leftPanel);
                suggestionDialog.add(rightPanel);

                // use cancel button to close dialog
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        suggestionDialog.dispose();
                    }
                });

                // use submit button to submit suggestion
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        
                        // check if accusation is correct
                        Card player = new Card((String) playerComboBox.getSelectedItem(), CardType.PERSON);
                        Card weapon = new Card((String) weaponComboBox.getSelectedItem(), CardType.WEAPON);
                        Card room = new Card((String) roomComboBox.getSelectedItem(), CardType.ROOM);
                        Solution accusation = new Solution(room, player, weapon);


                        if (board.checkAccusation(accusation)) {
                            JOptionPane.showMessageDialog(null, "<html><div style='text-align: center;'>Accusation was correct! " + currentPlayer.getName() + " wins!" + "\nThe solution was: " + accusation.toString());
                            // add a another message below showing the accuasion

                            System.exit(0);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "<html><div style='text-align: center;'>Accusation was incorrect! " + currentPlayer.getName() + " loses!" + "\nThe solution was: " + accusation.toString());
                            System.exit(0);
                        }
                    }
                });
            }
            else {
                // show it is not our turn
                JOptionPane.showMessageDialog(null, "It is not your turn.");
            }
        }
    };

    /**
     * Sets the guess to the given string
     * @param guess
     */
    public void setGuess(String guess) {
        this.guess = guess;
        guessTextField.setText(this.guess);
    }

    /**
     * Sets the guess result to the given string
     * @param result
     */
    public void setGuessResult(String result) {
        this.result = result;
        guessResultTextField.setText(this.result);
    }

    /**
     * Sets the turn to the given player
     * @param turn
     * @param roll
     * @param color
     */
    public void setTurn(Player turn, int roll, Color color) {
        this.turn = turn.getName();
        this.dieRoll = roll;
        turnTextField.setText(this.turn);
        turnTextField.setBackground(color);
        rollTextField.setText(Integer.toString(this.dieRoll));
    }

    public static void main(String[] args) {
        GameControlPanel panel = new GameControlPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(750, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Test filling in the data
        panel.setTurn(new ComputerPlayer("Col. Mustard", "orange", 0, 0), 5, Color.ORANGE);
        panel.setGuess("I have no guess!");
        panel.setGuessResult("So you have nothing?");
    }
}
