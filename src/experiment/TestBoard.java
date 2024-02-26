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

    final static int COLS = 4;
    final static int ROWS = 4;

    public TestBoard() {
        board = new TestBoardCell[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
            	board[i][j] = new TestBoardCell(i, j);   
            }
        }

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

    /*
     * public function to calculate the targets
     */
    public void calcTargets(TestBoardCell startCell, int pathLength) {
        visited = new HashSet<TestBoardCell>();
        targets = new HashSet<TestBoardCell>();
        visited.add(startCell);
        findTargets(startCell, pathLength);
        for(TestBoardCell cells: targets) {
        	System.out.println(cells);
        }
    }

    /*
     * private helper function to find the targets
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

    /*
     * returns cell at [row][col]
     */
    public TestBoardCell getCell(int row, int col) {
        return board[row][col];
    }

    /*
     * returns the targets
     */
    public Set<TestBoardCell> getTargets() {
        return targets;
    }

    
}
