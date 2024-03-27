package tests;

import static org.junit.Assert.assertEquals;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.Computer;

import clueGame.Board;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.Player;
import java.util.ArrayList;


public class GameSetupTest {
	
	private static Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
        board.deal();
	}
	
	
	@Test
    public void testPlayersLoaded(){
		assertEquals(board.getplayers().size(), 6);
		assertEquals(board.getplayers().get(0).getName(), "Paul");
		assertEquals(board.getplayers().get(2).getName(), "Eduard");
		assertEquals(board.getplayers().get(5).getName(), "Matt");
		assertEquals(board.getplayers().get(4).getName(), "Phinees");
		assertEquals(board.getplayers().get(3).getRow(), 13);
		assertEquals(board.getplayers().get(4).getCol(), 0);
    }
	
	@Test
	public void testSolutionDealt() {
		assert(board.getsolution().getPerson() != null);
		assert(board.getsolution().getRoom() != null);
		assert(board.getsolution().getWeapon() != null);
	}
	
	@Test
	public void testDeckCreated() {
		assertEquals(board.getCards().size(), 21);
		assertEquals(board.getCards().get(0).getCardName(), "Kitchen");
        assertEquals(board.getCards().get(11).getCardName(), "Eduard");
        assertEquals(board.getCards().get(5).getCardName(), "Kitchen");
        assertEquals(board.getCards().get(7).getCardType(), CardType.WEAPON);
        assertEquals(board.getCards().get(13).getCardType(), CardType.ROOM);
        assertEquals(board.getCards().get(19).getCardType(), CardType.PERSON);
		
	}

    @Test
    public void testHumanAndComputer() {
        ArrayList<Player> players = board.getplayers();
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
}
