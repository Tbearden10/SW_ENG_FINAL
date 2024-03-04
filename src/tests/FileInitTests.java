/*
Class Description: This class is used to test our board cells to ensure they are handled properly 
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
    // Constants to test if file was loaded correctly
    public static final int NUM_DOORS = 17;
    public static final int NUM_ROWS = 26;
    public static final int NUM_COLUMNS = 24;

    private static Board board;

    /**
     * Set up the board
     */
    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    /**
     * Test each room labels
     */
    @Test
    public void testRoomLabels() {
        assertEquals("Kitchen", board.getRoom('K').getName());
        assertEquals("Master Bedroom", board.getRoom('M').getName());
        assertEquals("Guest Bedroom", board.getRoom('G').getName());
        assertEquals("Dining Room", board.getRoom('D').getName());
        assertEquals("Living Room", board.getRoom('L').getName());
        assertEquals("Office", board.getRoom('O').getName());
        assertEquals("Basement", board.getRoom('B').getName());
        assertEquals("Entertainment Room", board.getRoom('E').getName());
        assertEquals("Shed", board.getRoom('S').getName());
    }

    /**
     * Test the number of rows and columns
     */
    @Test
    public void testBoardDimensions() {
        assertEquals(NUM_ROWS, board.getNumRows());
        assertEquals(NUM_COLUMNS, board.getNumColumns());
    }

    /**
     * Tests each doorway direction to ensure it is handled properly
     */
    @Test
    public void fourDoorDirections() {
        // Test a doorway for each direction (RIGHT/LEFT/UP/DOWN)
        // tests two cells that are not doorways (1 room and 1 walkway)
        // 6 total test cases

        // Down Test Case
        BoardCell cell = board.getCell(14, 5);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.DOWN, cell.getDoorDirection());

        // Up Test Case
        cell = board.getCell(4, 15);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.UP, cell.getDoorDirection());

        // Left Test Case
        cell = board.getCell(6, 3);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.LEFT, cell.getDoorDirection());

        // Right Test Case
        cell = board.getCell(15, 19);
        assertTrue(cell.isDoorway());
        assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());

        // Not a Doorway Test Case for Room
        cell = board.getCell(3, 10);
        assertFalse(cell.isDoorway());
        assertEquals(DoorDirection.NONE, cell.getDoorDirection());

        // Not a Doorway Test Case for Walkway
        cell = board.getCell(17, 16);
        assertFalse(cell.isDoorway());
        assertEquals(DoorDirection.NONE, cell.getDoorDirection());

    }

    /**
     * Test the number of doorways
     */
    @Test
    public void testNumberOfDoorways() {
        int numDoors = 0;
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumColumns(); col++) {
                BoardCell cell = board.getCell(row, col);
                if (cell.isDoorway()) {
                    numDoors++;
                }
            }
        }
        assertEquals(NUM_DOORS, numDoors);
    }

    /**
     * Test different cells for room locations and other special cells
     */
    @Test
    public void testRooms() {
        // Room location tests
        // 1 test for standard room location
        BoardCell cell = board.getCell(1, 2);
        Room room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Shed");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());
        assertFalse(cell.isDoorway());

        // 1 test for room label
        cell = board.getCell(4, 8);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Living Room");
        assertTrue(cell.isLabel());
        assertTrue(room.getLabelCell() == cell);

        // 1 test for room center
        cell = board.getCell(18, 3);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Dining Room");
        assertTrue(cell.isRoomCenter());
        assertTrue(room.getCenterCell() == cell);

        // 1 test for room secret passage
        cell = board.getCell(25, 9);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Basement");
        assertTrue(cell.getSecretPassage() == 'K');

        // 1 test for walkway
        cell = board.getCell(17, 16);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Walkway");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());

        // 1 test for closet/unused
        cell = board.getCell(14, 10);
        room = board.getRoom(cell);
        assertTrue(room != null);
        assertEquals(room.getName(), "Unused");
        assertFalse(cell.isLabel());
        assertFalse(cell.isRoomCenter());

    }


}
