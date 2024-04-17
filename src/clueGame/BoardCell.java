/*
Class Description: This class is used to handle the board cells and their properties
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package clueGame;

import java.util.Set;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;

public class BoardCell {

    private int row, col, width, height, x, y;

    private char initial;

    private DoorDirection doorDirection = DoorDirection.NONE;

    private boolean roomLabel;
    private boolean roomCenter;
    private boolean isDoorway;
    private boolean isOccupied;
    private boolean isVisable;

    private char secretPassage;

    private Set<BoardCell> adjList;

    /**
     * Constructor with default second character
     * @param row
     * @param col
     * @param initial
     */
    public BoardCell(int row, int col, char initial) {
    	this.row = row;
    	this.col = col;
    	this.initial = initial;
    	secretPassage = ' ';
    	adjList = new HashSet<BoardCell>();
    }
    /**
     * Constructor with special second character
     * @param row
     * @param col
     * @param initial
     * @param secondInitial
     */
    public BoardCell(int row, int col, char initial, char secondInitial) {
        this.row = row;
        this.col = col;
        this.initial = initial;
        secretPassage = secondInitial;
        adjList = new HashSet<BoardCell>();

        isVisable = false;

        // handle second char for direction and room label/center
        handleSecondChar(secondInitial);
    }

    /**
     * Helper function to handle a second character for a cell
     * @param secondInitial
     */
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
     * 
     * @param g
     * @param sideLen
     * @param x
     * @param y
     * @param isTarget
     */
    public void draw(Graphics g, int w, int h, int xpos, int ypos, boolean isTarget) {

        this.x = xpos;
        this.y = ypos;
        this.width = w;
        this.height = h;

        if (isRoom()) {
            g.setColor(Color.PINK);
            g.fillRect(x, y, width, height);
            //g.drawRect(x, y, width, height);    
        }
        else if (isUnused()){
            g.setColor(new Color(0f, 0f, 0f, 0f));
            g.drawRect(x, y, width, height);
        }
        else {
            g.setColor(new Color(0.8f, 0.8f, 0.8f, 0.9f));
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
        }

        if (isTarget && isVisable) {
            g.setColor(Color.CYAN);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
        }

        if (isDoorway) {
            g.setColor(Color.blue);
            switch (doorDirection) {
                case UP:
                    g.fillRect(x, y, width, height/5);
                    break;
                case DOWN:
                    g.fillRect(x, y + height - height/5, width, height/5);
                    break;
                case LEFT:
                    g.fillRect(x, y, width/5, height);
                    break;
                case RIGHT:
                    g.fillRect(x + width - width/5, y, width/5, height);
                    break;
                default:
                    break;
            }
        }
        
    }

    /**
     * Draws the room cell name
     */
    public void drawRoomCellName() {
        String name = Board.getInstance().getRoom(initial).getName();
        if (roomLabel) {
            Graphics g = Board.getInstance().getGraphics();
            g.setColor(Color.BLUE);
            g.drawString(name, this.x, this.y);
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
     * Returns whether or not the cell is a room
     * @return
     */
    public boolean isRoom() {
        return initial != 'W' && initial != 'X';
    }

    /**
     * Returns whether or not the cell is an unused space
     * @return
     */
    public boolean isUnused() {
        return initial == 'X';
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

    /**
     * Sets isOccupied member
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    /**
     * Returns isOccupied
     * @return
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }

    /**
     * Returns the row
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * Set the targets visible
     * @param visible
     */
    public void setIsVisible(boolean visible) {
        isVisable = visible;
    }

    /**
     * Returns whether or not the cell is visable
     * @return
     */
    public boolean isVisable() {
        return isVisable;
    }
}
