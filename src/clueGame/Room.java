/*
Class Description: Handles room properties for the game board and the cells
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package clueGame;

public class Room {

    private String name;

    private BoardCell centerCell;

    private BoardCell labelCell;


    /**
     * Constructor for the Room class
     * @param name
     */
    public Room(String name) {
        this.name = name;
    }

    /**
     * Get room name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the center cell of the room
     * @return
     */
    public BoardCell getCenterCell() {
        return centerCell;
    }
    
    public void setCenterCell(BoardCell cell) {
    	centerCell = cell;
    }

    /**
     * Get the label cell of the room
     * @return
     */
    public BoardCell getLabelCell() {
        return labelCell;
    }
    
    public void setLabelCell(BoardCell cell) {
    	labelCell = cell;
    }
    
}
