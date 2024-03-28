package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.Player;
import java.util.ArrayList;
import clueGame.Card;


public class GameSetupTest {
	
	private static Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
	}
	
	
	@Test
    public void testPlayersLoaded(){
		assertEquals(6, board.getPlayers().size());
		assertEquals(board.getPlayers().get(0).getName(), "Paul");
		assertEquals(board.getPlayers().get(2).getName(), "Eduard");
		assertEquals(board.getPlayers().get(5).getName(), "Matt");
		assertEquals(board.getPlayers().get(4).getName(), "Phinees");
		assertEquals(board.getPlayers().get(3).getRow(), 13);
		assertEquals(board.getPlayers().get(4).getCol(), 0);
    }
	
	
	@Test
	public void testDeckCreated() {
		assertEquals(board.getCards().size(), 21);
		assertEquals(board.getCards().get(0).getCardName(), "Kitchen");
        assertEquals(board.getCards().get(11).getCardName(), "Eduard");
        assertEquals(board.getCards().get(20).getCardName(), "Bomb");
        assertEquals(board.getCards().get(19).getCardType(), CardType.WEAPON);
        assertEquals(board.getCards().get(3).getCardType(), CardType.ROOM);
        assertEquals(board.getCards().get(10).getCardType(), CardType.PERSON);
		
	}

    @Test
    public void testHumanAndComputer() {
        ArrayList<Player> players = board.getPlayers();
        int humanCount = 0, computerCount = 0;

        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                humanCount++;
            } 
            else if (player instanceof ComputerPlayer) {
                computerCount++;
            }
        }

        assertEquals(humanCount, 1);
        assertEquals(computerCount, 5);

    }

    @Test
    public void testSolutionDelt() {
        board.deal();
        Solution solution = board.getSolution();
        Assert.assertNotNull(solution);
        Assert.assertNotNull(solution.getPerson());
        Assert.assertNotNull(solution.getRoom());
        Assert.assertNotNull(solution.getWeapon());

        assertEquals(solution.getPerson().getCardType(), CardType.PERSON);
        assertEquals(solution.getRoom().getCardType(), CardType.ROOM);
        assertEquals(solution.getWeapon().getCardType(), CardType.WEAPON);
        
    }

    @Test 
    public void testCardsDeltPlayers() {
        board.deal();
        ArrayList<Player> players = board.getPlayers();
        ArrayList<Card> boardCards = board.getCards();
        int totalPlayerCardCount = 0;

        for (Player player : players) {
            totalPlayerCardCount += player.getCards().size();
        }

        assertEquals(18, totalPlayerCardCount); // 3 cards delt to solution
        assertEquals(0,  boardCards.size());
        
        assertEquals(players.get(0).getCards().size(), 3);
        assertEquals(players.get(3).getCards().size(), 3);
    }
}
