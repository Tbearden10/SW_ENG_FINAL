/*
Class Description: This class is used to handle the board cells and their properties
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
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
    
    private boolean isWalkway;
    
    private boolean isDoorway;

    private char secretPassage;

    private Set<BoardCell> adjList;

    /**
     * 
     * @param row
     * @param col
     */
    public BoardCell(int row, int col, char initial) {
        this.row = row;
        this.col = col;
        this.initial = initial;
        adjList = new HashSet<BoardCell>();
    }

    /**
     * Adds a cell to the adjacency list
     * @param cell
     */
    public void addAdj(BoardCell cell) {
        adjList.add(cell);
    }

    public char getInitial() {
        return initial;
    }

    public void setIsDoorway(boolean doorway) {
        this.isDoorway = doorway;
    }
    
    /**
     * Returns whether or not the cell is a doorway
     * @return
     */
    public boolean isDoorway() {
        return isDoorway;
    }

    public void setIsWalkway(boolean walkway) {
        this.isWalkway = walkway;
    }

    /**
     * Returns whether or not the cell is a walkway
     * @return
     */
    public boolean isWalkway() {
        return isWalkway;
    }

    public void setDoorDirection(DoorDirection doorDirection) {
        this.doorDirection = doorDirection;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }


    public void setRoomLabel(boolean roomLabel) {
        this.roomLabel = roomLabel;
    }

    /**
     * Returns whether or not the cell is a room label
     * @return
     */
    public boolean isLabel() {
        return roomLabel;
    }

    public void setRoomCenter(boolean roomCenter) {
        this.roomCenter = roomCenter;
    }

    /**
     * Returns whether or not the cell is a room center
     * @return
     */
    public boolean isRoomCenter() {
        return roomCenter;
    }


    public void setSecretPassage(char secretPassage) {
        this.secretPassage = secretPassage;
    }

    /**
     * Returns the secret passage character
     * @return
     */
    public char getSecretPassage() {
        return secretPassage;
    }
}
