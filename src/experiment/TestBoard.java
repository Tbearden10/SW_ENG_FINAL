/*
Class Description: This class is used to setup our board for testing. 
Authors: Tanner Bearden and Brayden Clark
Date: 2/24/2024
Collaborators: n/a
Sources: n/a
 */
package experiment;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

public class TestBoard {

    TestBoardCell[][] board;

    Set<TestBoardCell> targets;

    public TestBoard() {}

    public void calcTargets(TestBoardCell startCell, int pathLength) {}

    public TestBoardCell getCell(int row, int col) {
        return new TestBoardCell(row, col);
    }

    public Set<TestBoardCell> getTargets() {
        return new TreeSet<TestBoardCell>();
    }

    
}
