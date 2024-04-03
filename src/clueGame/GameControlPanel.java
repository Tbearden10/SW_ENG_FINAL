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

public class GameControlPanel extends JPanel {

    private int dieRoll;
    private String guess;
    private String result;
    private String turn;
    private JTextField guessTextField;
    private JTextField guessResultTextField;
    private JTextField turnTextField;
    private JTextField rollTextField;

    public GameControlPanel() {
        super();
        initializePanels();
    }

    /**
     * Initializes the panels for the game control panel
     */
    private void initializePanels() {
        setLayout(new GridLayout(1, 2));

        // left panels holds guess and guess result
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        JPanel guessPanel = new JPanel();
        guessPanel.setLayout(new GridLayout(1, 1));
        guessPanel.setBorder(BorderFactory.createTitledBorder("Guess: "));
        guessTextField = new JTextField();
        guessTextField.setEditable(false);
        guessPanel.add(guessTextField);

        JPanel guessResultPanel = new JPanel();
        guessResultPanel.setLayout(new GridLayout(1, 1));
        guessResultPanel.setBorder(BorderFactory.createTitledBorder("Guess Result: "));
        guessResultTextField = new JTextField();
        guessResultTextField.setEditable(false);
        guessResultPanel.add(guessResultTextField);

        leftPanel.add(guessPanel);
        leftPanel.add(guessResultPanel);

        // right panel holds player curn and buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3, 1));

        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new GridLayout(1, 2));
        JLabel turnLabel = new JLabel("Whose Turn: ");
        turnTextField = new JTextField();
        turnTextField.setEditable(false);
        turnPanel.add(turnLabel);
        turnPanel.add(turnTextField);

        JPanel rollPanel = new JPanel();
        rollPanel.setLayout(new GridLayout(1, 2));
        JLabel rollLabel = new JLabel("Roll: ");
        rollTextField = new JTextField();
        rollTextField.setEditable(false);
        rollPanel.add(rollLabel);
        rollPanel.add(rollTextField);

        rightPanel.add(turnPanel);
        rightPanel.add(rollPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        JButton nextPlayer = new JButton("Next Player");
        JButton makeAccusation = new JButton("Make an Accusation");
        buttonPanel.add(nextPlayer);
        buttonPanel.add(makeAccusation);

        rightPanel.add(buttonPanel);

        add(leftPanel);
        add(rightPanel);
    }

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
