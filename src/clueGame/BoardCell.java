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

    private char secretPassage;

    private Set<BoardCell> adjList;

    /**
     * 
     * @param row
     * @param col
     */
    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
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
     * Returns whether or not the cell is a doorway
     * @return
     */
    public boolean isDoorway() {
        return false;
    }

    /**
     * Returns whether or not the cell is a walkway
     * @return
     */
    public boolean isWalkway() {
        return false;
    }

    public DoorDirection getDoorDirection() {
        return doorDirection;
    }

    /**
     * Returns whether or not the cell is a room label
     * @return
     */
    public boolean isLabel() {
        return roomLabel;
    }

    /**
     * Returns whether or not the cell is a room center
     * @return
     */
    public boolean isRoomCenter() {
        return roomCenter;
    }

    /**
     * Returns the secret passage character
     * @return
     */
    public char getSecretPassage() {
        return secretPassage;
    }
}