package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class ClueGame extends JFrame {

    private static Board board;
    private static GameControlPanel controlPanel;
    private static GameInformationPanel infoPanel;

    private static int WIDTH = 1200;
    private static int HEIGHT = 1150;
    
    public ClueGame() {

        // setup frame
        setSize(WIDTH, HEIGHT);
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
        for (Player player : board.getPlayers()) {
            if (player instanceof HumanPlayer) {
                infoPanel.updatePanels(player);
                infoPanel.repaint();
            }
        }

        // set layout sizes
        board.setPreferredSize(new Dimension(960,1040));
        board.setBackground(Color.RED);
        infoPanel.setPreferredSize(new Dimension(240, 1040));
        controlPanel.setPreferredSize(new Dimension(1200, 110));

        // add panels to frame
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
                infoPanel.repaint();
            }
        }
    }

    public static void setSuggestion(String guess) {
        controlPanel.setGuess(guess);
        controlPanel.repaint();
    }

    public static void main(String[] args) {
        ClueGame game = new ClueGame();
    }
}
