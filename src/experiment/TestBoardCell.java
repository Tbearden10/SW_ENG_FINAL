package experiment;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
public class TestBoardCell {

    private int row;
    private int col;
    private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();
    private boolean isRoom;
    private boolean isOccupied;


    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void addAdj(TestBoardCell cell) {
        adjList.add(cell);
    }

    public Set<TestBoardCell> getAdjList() {
        return adjList;
    }

    public void setIsOccupied(boolean isOccupied) {
        // nothin
    }

    public void setIsRoom(boolean isRoom) {
        // nothin
    }

    


}
