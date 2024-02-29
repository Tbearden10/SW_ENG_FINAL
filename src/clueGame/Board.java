/*
Class Description: Board class to handle the game board and its properties 
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package clueGame;

import java.util.Map;

public class Board {
    
    private BoardCell[][] grid;

    private int numRows;

    private int numColumns;

    private String layoutConfigFile;

    private String setupConfigFile;

    private Map<Character, Room> roomMap;

    /**
     * Variables and methods used for Singleton Pattern
     */
    private static Board theInstance = new Board();

    /**
     * Constructor
     */
    private Board() {
        super();
    }

    /**
     * Return the only board instance for Singleton Pattern
     * @return
     */
    public static Board getInstance() {
        return theInstance;
    }
    /**
     * Initializes the board
     */
    public void initialize() {
        
    }
    
    /**
     * Return the cell given column and row
     * @param row
     * @param col
     * @return
     */
    public BoardCell getCell(int row, int col) {
        return new BoardCell(row, col);
    }

    /**
     * Return the room give symbol
     * @param c
     * @return
     */
    public Room getRoom(char c) {
        return new Room("");
    }

    /**
     * return room given the cell
     * @param cell
     * @return
     */
    public Room getRoom(BoardCell cell) {
        return new Room("");
    }

    /**
     * Read the setup file
     */
    public void loadSetupConfig() {

    }
    /**
     * Read the layout file
     */
    public void loadLayoutConfig() {

    }
    /**
     * get the number of rows in the board
     * @return
     */
    public int getNumRows() {
        return 0;
    }
    /**
     * get the number of columns in the board
     * @return
     */
    public int getNumColumns() {
        return 0;
    }
    /**
     * set the configuration files from given files. 
     * @param layout
     * @param setup
     */
	public void setConfigFiles(String layout, String setup) {
		
	}

}
