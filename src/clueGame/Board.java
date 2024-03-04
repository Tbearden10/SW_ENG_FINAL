/*
Class Description: Board class to handle the game board and its properties 
Authors: Tanner Bearden and Brayden Clark
Date: 2/27/2024
Collaborators: n/a
Sources: n/a
 */
package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.Set;

public class Board {
    
    private BoardCell[][] grid;

    private int numRows;

    private int numColumns;

    private String layoutConfigFile;

    private String setupConfigFile;

    private Map<Character, Room> roomMap; // holds room cells

    private ArrayList<String[]> boardList; // assists when layout setup

    private Set<BoardCell> targets; // holds target cells


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

        numRows = 0;
        numColumns = 0;
        
        try {
            loadSetupConfig();
            loadLayoutConfig();
        } catch (FileNotFoundException | BadConfigFormatException e) {
            e.printStackTrace();
        }
     
    }

    
    public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
        roomMap = new HashMap<Character, Room>();
        File setupFile = new File(setupConfigFile);
        
       
        Scanner fileScanner = new Scanner(setupFile);

        while (fileScanner.hasNextLine()) {
            String fullString = fileScanner.nextLine();
            String[] line = fullString.split(", ");
            switch (line[0]) {
                case "Room": {
                    roomMap.put(line[2].charAt(0), new Room(line[1]));
                    break;
                }
                case "Space": {
                    roomMap.put(line[2].charAt(0), new Room(line[1]));
                    break;
                }
                default: {
                    if (line[0].length() != 0 && line[0].charAt(0) == '/') {
                        // do nothing
                    }
                    else if (fullString.length() != 0) {
                        throw new BadConfigFormatException("Invalid Setup format");
                    }
                }
            }
        }
        

        // close file scanner
        fileScanner.close();
       
    }

    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

        boardList = new ArrayList<String[]>();
         
         
        @SuppressWarnings("resource")
        Scanner fileScanner = new Scanner(new File(layoutConfigFile));
        
        String[] cells;


        // handles exceptions and calculates proper rows and columns 
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            cells = line.split(",");
             
            if (numColumns == 0) {
                numColumns = cells.length;
            }
            if (numColumns != cells.length) {
                throw new BadConfigFormatException("Missing Elements in Layout Config File");
            }
            if (cells.length != 0) {
                numRows++;
            }
            for (int i = 0; i < cells.length; i++) {
            	if (!roomMap.containsKey(cells[i].charAt(0))) {
            		throw new BadConfigFormatException("Bad Room Detected");
            	}
            }
             
            boardList.add(cells);
        }

        // private helper function that populate the grid with boardCells
        populateGrid();
         
        


        fileScanner.close();
    }

    private void populateGrid() {
        grid = new BoardCell[numRows][numColumns];

        // loop to add cells to grid
        for (int i=0; i < numRows; i++) {
            String[] cells = boardList.get(i);
            for (int j=0; j < numColumns; j++) {

                // adds cells to grid
                if (cells[j].length() > 1) {
                    grid[i][j] = new BoardCell(i, j, cells[j].charAt(0), cells[j].charAt(1));
                }
                else {
                    grid[i][j] = new BoardCell(i, j, cells[j].charAt(0), ' ');
                }

                // sets label cells and center cells
                char initial = grid[i][j].getInitial();
                char symbol = grid[i][j].getSecretPassage();
                
                switch (symbol) {
                    case '#':
                        roomMap.get(initial).setLabelCell(grid[i][j]);
                        break;
                    case '*':
                        roomMap.get(initial).setCenterCell(grid[i][j]);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public void calcTargets(BoardCell cell, int pathLength) {
        // calc the targets
    }

    public Set<BoardCell> getTargets() {
        return targets;
    }
    
    /**
     * Return the cell given column and row
     * @param row
     * @param col
     * @return
     */
    public BoardCell getCell(int row, int col) {
    	return grid[row][col];
    }

    /**
     * Return the room give symbol
     * @param c
     * @return
     */
    public Room getRoom(char c) {
        return roomMap.get(c);
    }

    /**
     * return room given the cell
     * @param cell
     * @return
     */
    public Room getRoom(BoardCell cell) {
    	return roomMap.get(cell.getInitial());
    }

    /**
     * Return the adjacency list from the given cell
     * @param name
     * @return
     */
    public Set<BoardCell> getAdjList(int row, int col) {
        return grid[row][col].getAdjList();
    }


    /**
     * get the number of rows in the board
     * @return
     */
    public int getNumRows() {
        return numRows;
    }
    /**
     * get the number of columns in the board
     * @return
     */
    public int getNumColumns() {
        return numColumns;
    }
    /**
     * set the configuration files from given files. 
     * @param layout
     * @param setup
     */
	public void setConfigFiles(String layout, String setup) {
		this.layoutConfigFile = "data/" + layout;
        this.setupConfigFile = "data/" + setup;
	}

}
