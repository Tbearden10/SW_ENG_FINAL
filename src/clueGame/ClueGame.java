package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {

    private static Board board;
    private static GameControlPanel controlPanel;
    private static GameInformationPanel infoPanel;

    private static int WIDTH = 1200;
    private static int HEIGHT = 1150;
    
    public ClueGame() {

        // setup frame
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clue Game");

        // setup board
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        board.deal();

        // setup panels
        controlPanel = new GameControlPanel();
        infoPanel = new GameInformationPanel();

        // add panels to frame
        for (Player p : board.getPlayers()) {
            if (p instanceof HumanPlayer) {
                infoPanel.updatePanels(p);
                infoPanel.repaint();
            }
        }


        // set layout sizes
        board.setPreferredSize(new Dimension(960,1040));
        board.setBackground(Color.RED);
        infoPanel.setPreferredSize(new Dimension(240, 1040));
        controlPanel.setPreferredSize(new Dimension(1200, 110));

        // add panels to frame
        board.addMouseListener(board.boardListener);
        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);

        setVisible(true);
        board.repaint();


        // display splash screen
        Player startingPlayer = board.getPlayers().get(0);
        String playerName = startingPlayer.getName();
        String message = "<html><div style='text-align: center;'>You are " + playerName + ".<br>Press Next Player to begin play</div></html>";
    
        JOptionPane splash = new JOptionPane();
        JOptionPane.showMessageDialog(splash, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Updates the information panel with the player's cards
     * @param player
     */
    public static void updateInfoPanel(Player player) {
        for (Player p : board.getPlayers()) {
            if (p instanceof HumanPlayer) {
                infoPanel.updatePanels(player);
                infoPanel.repaint();
            }
        }
    }

    /**
     * Sets the suggestion to the given string
     * @param guess
     */
    public static void setSuggestion(String guess) {
        controlPanel.setGuess(guess);
        controlPanel.repaint();
    }

    public static void main(String[] args) {
        ClueGame game = new ClueGame();

        Player startingPlayer = board.getPlayers().get(0);
        int startingRoll = startingPlayer.rollDie();
        String startingColor = startingPlayer.getColor();
        
        board.calcTargets(board.getCell(startingPlayer.getRow(), startingPlayer.getCol()), startingRoll);
        board.setTargetsVisible(true);

        controlPanel.setTurn(startingPlayer, startingRoll, Color.decode(startingColor));
        
    }
}
