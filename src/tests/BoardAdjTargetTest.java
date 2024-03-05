package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
    
    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    // Ensure that player does not move around within room
    // LIGHT BLUE on the planning spreadsheet
    @Test
    public void testAdjacenciesRooms() {

        // Test the basement, has 2 doors and secrete passage
        Set<BoardCell> testList = board.getAdjList(23, 11);
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(12, 21)));
        assertTrue(testList.contains(board.getCell(19, 10)));
        assertTrue(testList.contains(board.getCell(20, 13)));

        // test shed
        testList = board.getAdjList(4, 1);
        assertEquals(1, testList.size());
        assertTrue(testList.contains(board.getCell(6, 3)));

        // test master bedroom
        testList = board.getAdjList(23, 20);
        assertEquals(2, testList.size());
        assertTrue(testList.contains(board.getCell(20, 16)));
        assertTrue(testList.contains(board.getCell(20, 17)));
    }

    // Ensure door locations include their room and additional walkways
    // LIGHT BLUE on the planning spreadsheet
    @Test
    public void testAdjacenciesDoor() {

        Set<BoardCell> testList = board.getAdjList(20, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(20, 1)));
		assertTrue(testList.contains(board.getCell(20, 3)));
		assertTrue(testList.contains(board.getCell(18, 3)));

		testList = board.getAdjList(15, 19);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 18)));
		assertTrue(testList.contains(board.getCell(14, 19)));
		assertTrue(testList.contains(board.getCell(16, 19)));
		assertTrue(testList.contains(board.getCell(12, 21)));
		
		testList = board.getAdjList(8, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 13)));
		assertTrue(testList.contains(board.getCell(8, 14)));
		assertTrue(testList.contains(board.getCell(7, 13)));
		assertTrue(testList.contains(board.getCell(6, 10)));

    }

    // Test a varierity of walkway scenarios
    // DARK GREEN on the planning spreadsheet
    @Test
    public void testAdjacenciesWalkway() {

        // test bottom edge
        Set<BoardCell> testList = board.getAdjList(25, 13);
        assertEquals(1, testList.size());
        assertTrue(testList.contains(board.getCell(24, 13)));


        // test near door but not adjacent
        testList = board.getAdjList(16, 18);
        assertEquals(4, testList.size());
        assertTrue(testList.contains(board.getCell(15, 18)));
        assertTrue(testList.contains(board.getCell(16, 17)));
        assertTrue(testList.contains(board.getCell(17, 18)));
        assertTrue(testList.contains(board.getCell(16, 19)));


        // test adjacent to walkway
        testList = board.getAdjList(14, 3);
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(14, 2)));
        assertTrue(testList.contains(board.getCell(13, 3)));
        assertTrue(testList.contains(board.getCell(14, 4)));

        // test next to closet
        testList = board.getAdjList(13, 14);
        assertEquals(3, testList.size());
        assertTrue(testList.contains(board.getCell(12, 14)));
        assertTrue(testList.contains(board.getCell(14, 14)));
        assertTrue(testList.contains(board.getCell(13, 15)));
    }

    // Tests out of room center, 1 step, 2 steps, 4 steps
    // BLACK
    @Test
    public void testTargetsInShed() {

        // test roll of 1
        board.calcTargets(board.getCell(4, 1), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(1, targets.size());
        assertTrue(targets.contains(board.getCell(6, 3)));

        // test roll of 2
        board.calcTargets(board.getCell(4, 1), 2);
        targets = board.getTargets();
        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(5,3)));
        assertTrue(targets.contains(board.getCell(6,4)));
        assertTrue(targets.contains(board.getCell(7,3)));

        // test roll of 4
        board.calcTargets(board.getCell(4, 1), 4);
        targets = board.getTargets();
        assertEquals(10, targets.size());
        assertTrue(targets.contains(board.getCell(7,1)));
        assertTrue(targets.contains(board.getCell(5,3)));
        assertTrue(targets.contains(board.getCell(3,3)));
        assertTrue(targets.contains(board.getCell(7,5)));
    }

    // Tests out of room center, 1 step, 2 steps, 4 steps
    // BLACK
    @Test
    public void testTargetsInOffice() {

        // test roll of 1
        board.calcTargets(board.getCell(23, 2), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(20,2)));
        assertTrue(targets.contains(board.getCell(23,5)));

        // test roll of 2
        board.calcTargets(board.getCell(23,2), 2);
        targets = board.getTargets();
        assertEquals(5, targets.size());
        assertTrue(targets.contains(board.getCell(20,1)));
        assertTrue(targets.contains(board.getCell(22,5)));
        assertTrue(targets.contains(board.getCell(23,6)));


        // test roll of 4
        board.calcTargets(board.getCell(23,2), 4);
        targets = board.getTargets();
        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(20,5)));
        assertTrue(targets.contains(board.getCell(22,5)));
        assertTrue(targets.contains(board.getCell(23,8)));

    }

    // Tests out of room center, 1 step, 2 steps, 4 steps
    // BLACK
    @Test
    public void testTargetsInDoor() {

        // test roll of 1, at door
        board.calcTargets(board.getCell(8,13), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(9,13)));
        assertTrue(targets.contains(board.getCell(8,14)));
        assertTrue(targets.contains(board.getCell(7,13)));
        assertTrue(targets.contains(board.getCell(6,10)));

        
        // test roll of 2, at door
        board.calcTargets(board.getCell(8,13), 2);
        targets = board.getTargets();
        assertEquals(7, targets.size());
        assertTrue(targets.contains(board.getCell(9,14)));
        assertTrue(targets.contains(board.getCell(8,15)));
        assertTrue(targets.contains(board.getCell(7,14)));
        assertTrue(targets.contains(board.getCell(6,10)));


        // test roll of 4, at door
        board.calcTargets(board.getCell(8,13), 4);
        targets = board.getTargets();
        assertEquals(17, targets.size());
        assertTrue(targets.contains(board.getCell(9,10)));
        assertTrue(targets.contains(board.getCell(11,14)));
        assertTrue(targets.contains(board.getCell(4,13)));
        assertTrue(targets.contains(board.getCell(6,10)));

    }

    // Tests out of room center, 1 step, 2 steps, 4 steps
    // BLACK
    @Test
    public void testTargetsInWalkway1() {

        // test a roll of 1
        board.calcTargets(board.getCell(16, 16), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(16,15)));
        assertTrue(targets.contains(board.getCell(15,16)));
        assertTrue(targets.contains(board.getCell(16,17)));
        assertTrue(targets.contains(board.getCell(17,16)));

        // test a roll of 2
        board.calcTargets(board.getCell(16, 16), 2);
        targets = board.getTargets();
        assertEquals(8, targets.size());
        assertTrue(targets.contains(board.getCell(18,16)));
        assertTrue(targets.contains(board.getCell(16,18)));
        assertTrue(targets.contains(board.getCell(14,16)));
        assertTrue(targets.contains(board.getCell(16,14)));

        // test a roll of 4
        board.calcTargets(board.getCell(16, 16), 4);
        targets = board.getTargets();
        assertEquals(18, targets.size());
        assertTrue(targets.contains(board.getCell(13,15)));
        assertTrue(targets.contains(board.getCell(15,14)));
        assertTrue(targets.contains(board.getCell(20,16)));
        assertTrue(targets.contains(board.getCell(17,19)));
    }
    
    // Tests out of room center, 1 step, 2 steps, 4 steps
    // BLACK
    @Test
    public void testTargetsInWalkway2() {

        // test a roll of 1
        board.calcTargets(board.getCell(20, 1), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(20,2)));
        assertTrue(targets.contains(board.getCell(20,0)));

        // test a roll of 2
        board.calcTargets(board.getCell(20, 1), 2);
        targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(18,3)));
        assertTrue(targets.contains(board.getCell(20,3)));

        // test a roll of 4
        board.calcTargets(board.getCell(20, 1), 4);
        targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(20,5)));
        assertTrue(targets.contains(board.getCell(18,3)));

    }

    // Tests to ensure occupied locations do not cause problems
    // RED
    @Test
    public void testTargetsOccupied() {

        // test a roll of 3, blocked 1 up
        board.getCell(14, 17).setOccupied(true);
		board.calcTargets(board.getCell(15, 17), 3);
		board.getCell(14, 17).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(12, 21)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertFalse( targets.contains( board.getCell(14, 17)));


        // make sure we can get into a room, even if occupied
        board.getCell(23, 20).setOccupied(true);
		board.getCell(20, 18).setOccupied(true);
		board.calcTargets(board.getCell(20, 17), 1);
		board.getCell(20, 18).setOccupied(false);
		board.getCell(23, 20).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(19, 17)));	
		assertTrue(targets.contains(board.getCell(20, 16)));	
		assertTrue(targets.contains(board.getCell(23, 20)));

        // check leaving room with blocked doorway
        board.getCell(4, 17).setOccupied(true);
		board.calcTargets(board.getCell(3, 20), 2);
		board.getCell(4, 17).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(4, 14)));
		assertTrue(targets.contains(board.getCell(4, 15)));	
		assertTrue(targets.contains(board.getCell(4, 16)));
		assertTrue(targets.contains(board.getCell(5, 15)));
		assertTrue(targets.contains(board.getCell(5, 16)));
    }
}
