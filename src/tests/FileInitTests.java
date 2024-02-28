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
    public static final int LEGEND_SIZE = 11;
    public static final int NUM_ROWS = 26;
    public static final int NUM_COLUMNS = 23;

    private static Board board;

    @BeforeAll
    public static void setUp() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

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
}