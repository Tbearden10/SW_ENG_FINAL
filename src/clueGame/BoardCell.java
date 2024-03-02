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

    private DoorDirection doorDirection = DoorDirection.NONE;

    private boolean roomLabel;

    private boolean roomCenter;
    
    private boolean isWalkway;
    
    private boolean isDoorway = false;

    private char secretPassage;

    private Set<BoardCell> adjList;

    /**
     * 
     * @param row
     * @param col
     * @param initial
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


    /**
     * Returns the cell initial
     * @return
     */
    public char getInitial() {
        return initial;
    }

    /**
     * Sets isDoorway member
     * @param doorway
     */
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

    /**
     * Sets the isWalkway member
     * @param walkway
     */
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

    /**
     * Sets the door direction
     * @param doorDirection
     */
    public void setDoorDirection(DoorDirection doorDirection) {
        this.doorDirection = doorDirection;
    }

    /**
     * Returns the door direction
     * @return
     */
    public DoorDirection getDoorDirection() {
        return doorDirection;
    }


    /**
     * Sets the isRoomLabel member
     * @param roomLabel
     */
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

    /**
     * Sets the isRoomCenter member
     * @param roomCenter
     */
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

    /**
     * Sets the secret passage character
     * @param secretPassage
     */
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
