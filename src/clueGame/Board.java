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

public class Board {
    
    private BoardCell[][] grid;

    private int numRows;

    private int numColumns;

    private String layoutConfigFile;

    private String setupConfigFile;

    private Map<Character, Room> roomMap;

    ArrayList<String[]> boardList;

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
        
        try {
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
                            continue;
                        }
                        else if (fullString.length() != 0) {
                            throw new BadConfigFormatException("Invalid Setup format");
                        }
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

         boardList = new ArrayList<String[]>();
         
         Scanner fileScanner;
         fileScanner = new Scanner(new File(layoutConfigFile));
        
         String[] cells;
         
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

         
         
         grid = new BoardCell[numRows][numColumns];

         for (int i = 0; i < numRows; i++) {
             String[] line = boardList.get(i);
             
             
             for (int j = 0; j < numColumns; j++) {
                 grid[i][j] = new BoardCell(i, j, line[j].charAt(0));

                 if (line[j].length() == 1) {
                     switch(line[j].charAt(0)) {
                         case 'W': {
                             grid[i][j].setIsWalkway(true);
                             grid[i][j].setIsDoorway(false);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setRoomCenter(false);
                             break;
                         }
                         default: {
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setIsDoorway(false);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setRoomCenter(false);
                             break;
                         }
                     }
                 } 
                 else if (line[j].length() == 2) {
                     switch (line[j].charAt(1)) {
                         case '#': {
                             grid[i][j].setRoomLabel(true);
                             grid[i][j].setRoomCenter(false);
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setIsDoorway(false);
                         }
                         case '*': {
                             grid[i][j].setRoomCenter(true);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setIsDoorway(false);
                         }
                         case '^':
                             grid[i][j].setIsDoorway(true);
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setRoomCenter(false);
                             grid[i][j].setDoorDirection(DoorDirection.UP);
                         case 'v':
                             grid[i][j].setIsDoorway(true);
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setRoomCenter(false);
                             grid[i][j].setDoorDirection(DoorDirection.DOWN);
                         case '<':
                             grid[i][j].setIsDoorway(true);
                             grid[i][j].setIsWalkway(false);
                             grid[i][j].setRoomLabel(false);
                             grid[i][j].setRoomCenter(false);
                             grid[i][j].setDoorDirection(DoorDirection.LEFT);
                         case '>': {
                      
                             grid[i][j].setDoorDirection(DoorDirection.RIGHT);
                         }
                         default:
                             grid[i][j].setSecretPassage(line[j].charAt(1));
                     }
                 }
             }
         }
          
         fileScanner.close();
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
		this.layoutConfigFile = layout;
        this.setupConfigFile = setup;
	}

}
