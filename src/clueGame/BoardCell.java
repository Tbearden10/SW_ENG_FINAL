package clueGame;

import java.util.Set;
import java.util.HashSet;

public class BoardCell {

    private int row;
    
    private int col;

    private char initial;

    private DoorDirection doorDirection;

    private boolean roomLabel;

    private boolean roomCenter;

    private char secretPassage;

    private Set<BoardCell> adjList;

    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        adjList = new HashSet<BoardCell>();
    }

    public void addAdj(BoardCell cell) {
        adjList.add(cell);
    }

    public boolean isDoorway() {
        return false;
    }

    public boolean isWalkway() {
        return false;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    public boolean isLabel() {
        return roomLabel;
    }

    public boolean isRoomCenter() {
        return roomCenter;
    }

    public char getSecretPassage() {
        return secretPassage;
    }
}