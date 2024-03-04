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

    @SuppressWarnings("unused")
    private int row;
    
    @SuppressWarnings("unused")
    private int col;

    private char initial;

    private DoorDirection doorDirection = DoorDirection.NONE;

    private boolean roomLabel;

    private boolean roomCenter;
    
    private boolean isDoorway;

    private boolean isOccupied;

    private char secretPassage;

    private Set<BoardCell> adjList;

    /**
     * 
     * @param row
     * @param col
     * @param initial
     */
    public BoardCell(int row, int col, char initial, char secondInitial) {
        this.row = row;
        this.col = col;
        this.initial = initial;
        secretPassage = secondInitial;
        adjList = new HashSet<BoardCell>();

        // handle second char for direction and room label/center
        handleSecondChar(secondInitial);
    }

    private void handleSecondChar(char secondInitial) {
        // switch to handle second char
        switch (secondInitial) {
            case '#':
                roomLabel = true;
                break;
            case '*':
                roomCenter = true;
                break;
            case '^':
            case 'v':
            case '<':
            case '>':
                isDoorway = true;
                setDoorDirection(secondInitial);
                break;
            default:
                break;
        }
    }

    /**
     * Adds a cell to the adjacency list
     * @param cell
     */
    public void addAdj(BoardCell cell) {
        adjList.add(cell);
    }

    
    /**
     * Returns the adjacency list
     * @return
     */
    public Set<BoardCell> getAdjList() {
        return adjList;
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
     * Sets the door direction
     * @param doorDirection
     */
    public void setDoorDirection(DoorDirection doorDirection) {
        this.doorDirection = doorDirection;
    }

    /**
     * Sets door direction based on char input
     * @param doorDirection
     */
    private void setDoorDirection(char doorDirectionChar) {

        // handles char input
        switch (doorDirectionChar) {
            case '^':
                doorDirection = DoorDirection.UP;
                break;
            case 'v':
                doorDirection = DoorDirection.DOWN;
                break;
            case '>':
                doorDirection = DoorDirection.RIGHT;
                break;
            case '<':
                doorDirection = DoorDirection.LEFT;
                break;
            default:
                doorDirection = DoorDirection.NONE;
        }
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

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}
