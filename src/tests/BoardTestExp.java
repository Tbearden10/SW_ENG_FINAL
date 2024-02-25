package tests;

import experiment.TestBoard;
import experiment.TestBoardCell;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTestExp {
   TestBoard board;

   public BoardTestExp() {
   }

   @BeforeEach
   public void setUp() {
      this.board = new TestBoard();
   }

  

   /*
    * Tests adjacency for top left
    */
   @Test
   public void testAdjacencyTopLeft() {
      TestBoardCell cell = this.board.getCell(0, 0);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(1, 0)));
      Assert.assertTrue(testList.contains(this.board.getCell(0, 1)));
      Assert.assertEquals(2L, (long)testList.size());
   }

   /*
    * Tests adjacency for bottom right
    */
   @Test
   public void testAdjacencyBottomRight() {
      TestBoardCell cell = this.board.getCell(3, 3);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(3, 2)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertEquals(2L, (long)testList.size());
   }
   
   
   /*
    * Tests adjacency for a right edge
    */
   @Test
   public void testAdjacencyRightEdge() {
      TestBoardCell cell = this.board.getCell(1, 3);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(0, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(1, 2)));
      Assert.assertEquals(3L, (long)testList.size());
   }

   /*
    * Tests adjacency for a left edge
    */
   @Test
   public void testAdjacencyLeftEdge() {
      TestBoardCell cell = this.board.getCell(3, 0);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(2, 0)));
      Assert.assertTrue(testList.contains(this.board.getCell(3, 1)));
      Assert.assertEquals(2L, (long)testList.size());
   }

   /*
    * Tests adjacency for middle of board
    */
   @Test
   public void testAdjacencyMiddle() {
      TestBoardCell cell = this.board.getCell(2, 2);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(1, 2)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 1)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(3, 2)));
      Assert.assertEquals(4L, (long)testList.size());
   }

   /*
    * Tests targets with several rolls and start locations
    */
   @Test
   public void testTargetsNormal() {
      TestBoardCell cell = this.board.getCell(0, 0);
      this.board.calcTargets(cell, 3);
      Set<TestBoardCell> targets = this.board.getTargets();
      Assert.assertEquals(6L, (long)targets.size());
      Assert.assertTrue(targets.contains(this.board.getCell(3, 0)));
      Assert.assertTrue(targets.contains(this.board.getCell(2, 1)));
      Assert.assertTrue(targets.contains(this.board.getCell(0, 1)));
      Assert.assertTrue(targets.contains(this.board.getCell(1, 2)));
      Assert.assertTrue(targets.contains(this.board.getCell(0, 3)));
      Assert.assertTrue(targets.contains(this.board.getCell(1, 0)));
   }

   /*
    * Tests targets with rooms and occupied cells
    */
   @Test
   public void testTargetsMixed() {
      this.board.getCell(0, 2).setIsOccupied(true);
      this.board.getCell(1, 2).setIsRoom(true);
      TestBoardCell cell = this.board.getCell(0, 3);
      this.board.calcTargets(cell, 3);
      Set<TestBoardCell> targets = this.board.getTargets();
      Assert.assertEquals(3L, (long)targets.size());
      Assert.assertTrue(targets.contains(this.board.getCell(1, 2)));
      Assert.assertTrue(targets.contains(this.board.getCell(2, 2)));
      Assert.assertTrue(targets.contains(this.board.getCell(3, 3)));
   }
   
   /*
    * Tests targets with room
    */
   @Test
   public void testTargetsRooms() {
	   this.board.getCell(0,0).setIsRoom(true);
	   this.board.getCell(3,0).setIsRoom(true);
	   this.board.getCell(3,3).setIsRoom(true);
	   TestBoardCell cell = this.board.getCell(0, 1);
	   this.board.calcTargets(cell, 1);
	   Set<TestBoardCell> targets = this.board.getTargets();
	   Assert.assertEquals(2L, (long)targets.size());
	   Assert.assertTrue(targets.contains(board.getCell(0, 2)));
	   Assert.assertTrue(targets.contains(board.getCell(1, 1)));
   }
   
   /*
    * Tests for targets with space is occupied
    */
   @Test
   public void testTargetsOccupied() {
	   this.board.getCell(2,2).setIsOccupied(true);
	   this.board.getCell(2,0).setIsOccupied(true);
	   this.board.getCell(0,3).setIsOccupied(true);
	   TestBoardCell cell = this.board.getCell(3, 2);
	   this.board.calcTargets(cell, 3);
	   Set<TestBoardCell> targets = this.board.getTargets();
	   Assert.assertEquals(2L, (long)targets.size());
	   Assert.assertTrue(targets.contains(board.getCell(1, 3)));
	   Assert.assertTrue(targets.contains(board.getCell(1, 1)));
   }
   
   /*
    * Tests for targets with max roll
    */
   @Test
   public void testTargetsMaxDieRoll() {
	   TestBoardCell cell = this.board.getCell(0, 3);
	   this.board.calcTargets(cell, 6);
	   Set<TestBoardCell> targets = this.board.getTargets();
	   Assert.assertEquals(7L, (long)targets.size());
	   Assert.assertTrue(targets.contains(board.getCell(0, 1)));
	   Assert.assertTrue(targets.contains(board.getCell(1, 0)));
	   Assert.assertTrue(targets.contains(board.getCell(1, 2)));
	   Assert.assertTrue(targets.contains(board.getCell(2, 1)));
	   Assert.assertTrue(targets.contains(board.getCell(2, 3)));
	   Assert.assertTrue(targets.contains(board.getCell(3, 0)));
	   Assert.assertTrue(targets.contains(board.getCell(3, 2)));
   }
}
