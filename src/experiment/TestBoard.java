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

public class TestBoard {

    private TestBoardCell[][] board;

    private Set<TestBoardCell> targets;

    private Set<TestBoardCell> visited;

    static final int COLS = 4;
    static final int ROWS = 4;

    /**
     * Constructor for TestBoard
     */
    public TestBoard() {
        board = new TestBoardCell[ROWS][COLS];

        // loop through the board and create new cells
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
            	board[i][j] = new TestBoardCell(i, j);   
            }
        }

        // call helper function
        calcAdj(board);
    }

    /**
     * helper function to calculate the adjacencies
     */
    private void calcAdj(TestBoardCell[][] board) {
        // calculate adjacencies
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i > 0) {
                    board[i][j].addAdj(board[i - 1][j]);
                }
                if (i < ROWS - 1) {
                    board[i][j].addAdj(board[i + 1][j]);
                }
                if (j > 0) {
                    board[i][j].addAdj(board[i][j - 1]);
                }
                if (j < COLS - 1) {
                    board[i][j].addAdj(board[i][j + 1]);
                }
            }
        }
    }

    /**
     * Calculates the targets based on the start cell and path length
     * @param startCell
     * @param pathLength
     */
    public void calcTargets(TestBoardCell startCell, int pathLength) {
        visited = new HashSet<TestBoardCell>();
        targets = new HashSet<TestBoardCell>();
        visited.add(startCell);
        findTargets(startCell, pathLength);
    }

    /**
     * Recursive helper function to find the targets
     * @param startCell
     * @param pathLength
     */
    private void findTargets(TestBoardCell startCell, int pathLength) {
        if(pathLength == 0) {
        	targets.add(startCell);
        }
        else {
        	for(TestBoardCell adjCell: startCell.getAdjList()) {
        		if(adjCell.getIsOccupied()) {
        			visited.add(adjCell);
        		}
        		if(!visited.contains(adjCell)) {
        			visited.add(adjCell);
        			if(adjCell.getIsRoom()) {
        				targets.add(adjCell);
        			}
        			else {
        				findTargets(adjCell, pathLength - 1);
        			}
        			visited.remove(adjCell);
        		}
        	}
        }
    }

    /**
     * Returns the cell at the given row and column
     * @param row
     * @param col
     * @return
     */
    public TestBoardCell getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns the targets list
     * @return
     */
    public Set<TestBoardCell> getTargets() {
        return targets;
    }

    
}
