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

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


public class GameControlPanel extends JPanel {

    private int dieRoll;
    private String guess;
    private String result;
    private String turn;

    /**
     * Constructor for the GameControlPannel class
     */
    public GameControlPanel() {
        super();
        
       // create all sub panels and buttons and then go and add to the larger ones
       setLayout(new GridLayout(1, 2));


       JPanel leftPanel = new JPanel();
       leftPanel.setLayout(new GridLayout(2, 1));

       // 1x0 with border and text field
       JPanel guessPanel = new JPanel();
       guessPanel.setLayout(new GridLayout(1, 1));
       guessPanel.setBorder(BorderFactory.createTitledBorder("Guess"));
       JTextField guessTextField = new JTextField();
       guessPanel.add(guessTextField);

       // 1x0 with border and text field
       JPanel guessResultPanel = new JPanel();
       guessResultPanel.setLayout(new GridLayout(1, 1));
       guessResultPanel.setBorder(BorderFactory.createTitledBorder("Guess Result"));
       JTextField guessResultTextField = new JTextField();
       guessResultPanel.add(guessResultTextField);

       leftPanel.add(guessPanel);
       leftPanel.add(guessResultPanel);


       JPanel rightPanel = new JPanel();
       rightPanel.setLayout(new GridLayout(2, 2));

        //shows turn (centered left) has jlabel and text field inside
        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new GridLayout(1, 2));
        JLabel turnLabel = new JLabel("Turn: ");
        JTextField turnTextField = new JTextField();
        turnPanel.add(turnLabel);
        turnPanel.add(turnTextField);

        // shows roll (centered right) has jlabel and text field inside
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(1, 2));
        JLabel playerLabel = new JLabel("Player: ");
        JTextField playerTextField = new JTextField();
        playerPanel.add(playerLabel);
        playerPanel.add(playerTextField);
        rightPanel.add(turnPanel);
        rightPanel.add(playerPanel);

        // button panel containing both buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton nextPlayer = new JButton("Next Player");
        JButton makeAccusation = new JButton("Make an Accusation");
        buttonPanel.add(nextPlayer);
        buttonPanel.add(makeAccusation);
        rightPanel.add(nextPlayer);
        rightPanel.add(makeAccusation);

        // Add everything to the main panel
        add(leftPanel);
        add(rightPanel);
    }

    
    public void setGuess(String guess) {
        this.guess = guess;
    }

    public void setGuessResult(String result) {
        this.result = result;
    }

    public void setTurn(Player turn, int roll) {
        this.turn = turn.getName();
        this.dieRoll = roll;
    }

    /**
     * Main to test the panel
     * @param args
     */
    public static void main(String[] args) {
        GameControlPanel panel = new GameControlPanel();  // create the panel (2x0)
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close

      
        
        frame.setVisible(true);

		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");

        
    }

}
