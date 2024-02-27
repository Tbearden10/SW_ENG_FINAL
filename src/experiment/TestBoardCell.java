/*
Class Description: This class is used to setup and test board cells
Authors: Tanner Bearden and Brayden Clark
Date: 2/24/2024
Collaborators: n/a
Sources: n/a
 */

package experiment;

import java.util.Set;
import java.util.HashSet;


public class TestBoardCell {

    private int row, col;
    private boolean isRoom, isOccupied;

    Set<TestBoardCell> adjList;

    /**
     * Constructor for TestBoardCell
     * @param row
     * @param col
     */
    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        adjList = new HashSet<TestBoardCell>();
    }

    /**
     * Adds an adjacent cell to the adjList
     * @param cell
     */
    public void addAdj(TestBoardCell cell) {
        adjList.add(cell);
    }

    /**
     * Returns the adjacency list
     * @return
     */
    public Set<TestBoardCell> getAdjList() {
        return adjList;
    }

    /**
     * Sets isOccupied member
     * @param isOccupied
     */
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    /**
     * Sets isRoom member
     * @param isRoom
     */
    public void setIsRoom(boolean isRoom) {
        this.isRoom = isRoom;
    }

    /**
     * Returns isOccupied
     * @return
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }

    /**
     * Returns isRoom
     * @return
     */
    public boolean getIsRoom() {
        return isRoom;
    }

	@Override
	public String toString() {
		return "TestBoardCell [row=" + row + ", col=" + col + "]";
	}
    

    


}
