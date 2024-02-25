package tests;

import experiment.TestBoard;
import experiment.TestBoardCell;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
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

   @AfterEach
   public void cleanup() {
   }

   @Test
   public void testAdjacency1() {
      TestBoardCell cell = this.board.getCell(0, 0);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(1, 0)));
      Assert.assertTrue(testList.contains(this.board.getCell(0, 1)));
      Assert.assertEquals(2L, (long)testList.size());
   }

   @Test
   public void testAdjacency2() {
      TestBoardCell cell = this.board.getCell(3, 3);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(3, 2)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertEquals(2L, (long)testList.size());
   }

   @Test
   public void testAdjacency3() {
      TestBoardCell cell = this.board.getCell(1, 3);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(0, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(1, 2)));
      Assert.assertEquals(3L, (long)testList.size());
   }

   @Test
   public void testAdjacency4() {
      TestBoardCell cell = this.board.getCell(3, 0);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(2, 0)));
      Assert.assertTrue(testList.contains(this.board.getCell(3, 1)));
      Assert.assertEquals(2L, (long)testList.size());
   }

   @Test
   public void testAdjacency5() {
      TestBoardCell cell = this.board.getCell(2, 2);
      Set<TestBoardCell> testList = cell.getAdjList();
      Assert.assertTrue(testList.contains(this.board.getCell(1, 2)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 1)));
      Assert.assertTrue(testList.contains(this.board.getCell(2, 3)));
      Assert.assertTrue(testList.contains(this.board.getCell(3, 2)));
      Assert.assertEquals(4L, (long)testList.size());
   }

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

   @Test
   public void testTargetsMixed() {
      this.board.getCell(0, 2).setOccupied(true);
      this.board.getCell(1, 2).setIsRoom(true);
      TestBoardCell cell = this.board.getCell(0, 3);
      this.board.calcTargets(cell, 3);
      Set<TestBoardCell> targets = this.board.getTargets();
      Assert.assertEquals(3L, (long)targets.size());
      Assert.assertTrue(targets.contains(this.board.getCell(1, 2)));
      Assert.assertTrue(targets.contains(this.board.getCell(2, 2)));
      Assert.assertTrue(targets.contains(this.board.getCell(3, 3)));
   }
}
