package tests;

import static org.junit.Assert.assertEquals;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.Solution;


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
		assert(board.getCards().get(0).getCardName() != null);
		assert(board.getCards().get(5).getCardName() != null);
		assert(board.getCards().get(8).getCardName() != null);
		assert(board.getCards().get(14).getCardName() != null);
		assert(board.getCards().get(19).getCardName() != null);
		
	}
}
