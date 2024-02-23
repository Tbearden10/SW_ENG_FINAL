package experiment;

import java.util.Set;
import java.util.TreeSet;
public class TestBoardCell {

    private int row;
    private int col;
    private Set<TestBoardCell> adjList;
    private boolean isRoom;
    private boolean isOccupied;


    public TestBoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        adjList = new TreeSet<TestBoardCell>();
    }


    public Set<TestBoardCell> getAdjList() {
        return null;
    }

    


}
