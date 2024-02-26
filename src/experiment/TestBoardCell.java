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

    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        adjList = new HashSet<TestBoardCell>();
    }

    public void addAdj(TestBoardCell cell) {
        adjList.add(cell);
    }

    public Set<TestBoardCell> getAdjList() {
        return adjList;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public void setIsRoom(boolean isRoom) {
        this.isRoom = isRoom;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public boolean getIsRoom() {
        return isRoom;
    }

    


}
