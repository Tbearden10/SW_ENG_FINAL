package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

    private static Board board;
    private static GameControlPanel controlPanel;
    private static GameInformationPanel infoPanel;

    private static int WIDTH = 950;
    private static int HEIGHT = 950;
    
    public ClueGame() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clue Game");

        // setup board
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        board.deal();

        
        controlPanel = new GameControlPanel();
        

        infoPanel = new GameInformationPanel();
        for (Player player : board.getPlayers()) {
            if (player instanceof HumanPlayer) {
                infoPanel.updatePanels(player);
                infoPanel.repaint();
            }
        }

        board.setBackground(java.awt.Color.BLACK);
       
        infoPanel.setPreferredSize(new Dimension(150, 600));
        controlPanel.setPreferredSize(new Dimension(800, 125));

        add(board, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(infoPanel, BorderLayout.EAST);

        setVisible(true);
        board.repaint();
    }

    public static void updateInfoPanel(Player player) {
        for (Player p : board.getPlayers()) {
            if (p instanceof HumanPlayer) {
                infoPanel.updatePanels(player);
            }
        }
    }

    public static void setSuggestion(String guess) {
        controlPanel.setGuess(guess);
        controlPanel.repaint();
        controlPanel.revalidate();
    }

    public static void main(String[] args) {
        ClueGame game = new ClueGame();
    }
}
