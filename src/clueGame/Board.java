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
import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.Set;

import java.util.HashSet;

public class Board {
    
    private BoardCell[][] grid;

    private int numRows;

    private int numColumns;

    private String layoutConfigFile;
    
    public static Solution solution;

    private String setupConfigFile;
    
    private ArrayList<Card> cards;
    
    private ArrayList<Player> players;

    private Map<Character, Room> roomMap; // holds room cells

    private ArrayList<String[]> boardList; // assists when layout setup

    private Set<BoardCell> targets; // holds target cells

    private Set<BoardCell> visited; // holds visited cells

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

    /**
     * Reads in the setup file to handle the room types 
     * Handles any errors that may be found in the setup file
     * @throws FileNotFoundException
     * @throws BadConfigFormatException
     */
    public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
        roomMap = new HashMap<Character, Room>();
        cards = new ArrayList<Card>();
        players = new ArrayList<Player>();
        File setupFile = new File(setupConfigFile);
        
       
        Scanner fileScanner = new Scanner(setupFile);

        while (fileScanner.hasNextLine()) {
            String fullString = fileScanner.nextLine();
            String[] line = fullString.split(", ");
            switch (line[0]) {
                case "Room": {
                    roomMap.put(line[2].charAt(0), new Room(line[1]));
                    cards.add(new Card(line[1], CardType.ROOM));
                    break;
                }
                case "Player": {
                    if (line[1].equals("Human")) {
                        players.add(new HumanPlayer(line[2], line[3], Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                    }
                    else if (line[1].equals("Computer")){
                        players.add(new ComputerPlayer(line[2], line[3], Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                    }
                    cards.add(new Card(line[2], CardType.PERSON));
                    break;
                }
                case "Weapon": {
                    cards.add(new Card(line[1], CardType.WEAPON));
                    break;
                }
                case "Space": {
                    roomMap.put(line[2].charAt(0), new Room(line[1]));
                    break;
                }
                default: {
                    if (fullString.length() != 0 && line[0].length() == 0 && line[0].charAt(0) != '/') {
                        throw new BadConfigFormatException("Invalid Setup format");
                    }
                }
            }
        }
        

        // close file scanner
        fileScanner.close();
       
    }

    /**
     * Handles any errors within the setup config file
     * Also calculates the correct number of rows and columns
     * @throws FileNotFoundException
     * @throws BadConfigFormatException
     */
    public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {

        boardList = new ArrayList<String[]>();
        
        Scanner fileScanner = new Scanner(new File(layoutConfigFile));
        
        // handles exceptions and calculates proper rows and columns 
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] cells = line.split(",");
             
            // base case
            if (numColumns == 0) {
                numColumns = cells.length;
            }

            // Error handling
            if (numColumns != cells.length) {
                throw new BadConfigFormatException("Missing Elements in Layout Config File");
            }

            // increment rows for each line (if line is not empty)
            if (cells.length != 0) {
                numRows++;
            }
            
            // loop through cells and ensure the character is valid
            for (int i = 0; i < cells.length; i++) {
            	if (!roomMap.containsKey(cells[i].charAt(0))) {
            		throw new BadConfigFormatException("Bad Room Detected");
            	}
            }
             
            boardList.add(cells);
        }

        // private helper function that populate the grid with boardCells
        populateGrid();
        
        calcAdjacencies(grid);

        fileScanner.close();

    }

    /**
     * Private helper function to calculate the adjacencies of the board
     */
    private void calcAdjacencies(BoardCell[][] grid) {


        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
            	// walkways
                if (grid[i][j].getInitial() == 'W') {
                    // check each direction
                    if (i > 0 && grid[i-1][j].getInitial() == 'W') {
                        grid[i][j].addAdj(grid[i-1][j]);
                    }
                    if (i < numRows - 1 && grid[i+1][j].getInitial() == 'W') {
                        grid[i][j].addAdj(grid[i+1][j]);
                    }
                    if (j > 0 && grid[i][j-1].getInitial() == 'W') {
                        grid[i][j].addAdj(grid[i][j-1]);
                    }
                    if (j < numColumns - 1 && grid[i][j+1].getInitial() == 'W') {
                        grid[i][j].addAdj(grid[i][j+1]);
                    }


                    // handle doorways
                    if (grid[i][j].isDoorway()) {
                        setDoorway(grid[i][j].getDoorDirection(), i, j);
                    }
                }
                
                // Check for secret passage
            	char passage = grid[i][j].getSecretPassage();
            	char initial = grid[i][j].getInitial();
            	// Ensure that other spaces are not identified as secret passages
                if(Character.isLetter(passage) && passage != 'v') {
                	
                	// Add secret passage cells to each others adjacencies 
                	roomMap.get(passage).getCenterCell().addAdj(roomMap.get(initial).getCenterCell());
                    roomMap.get(initial).getCenterCell().addAdj(roomMap.get(passage).getCenterCell());
                }

            }
        }
        
       
    }

    /**
     * Set the doorway of the given cell
     * @param row
     * @param col
     */
    private void setDoorway(DoorDirection direction, int row, int col) {
        int i = row;

        int j = col;

        switch (direction) {


            case UP:
            	// Uses initial to find center boardcell of doorway based on direction
                BoardCell adjToAdd = roomMap.get(grid[i-1][j].getInitial()).getCenterCell();
                
                grid[i][j].addAdj(adjToAdd);
                    
                adjToAdd.addAdj(grid[i][j]);
                
                break;
            case DOWN:
            	// Uses initial to find center boardcell of doorway based on direction
                adjToAdd = roomMap.get(grid[i+1][j].getInitial()).getCenterCell();

                grid[i][j].addAdj(adjToAdd);

                adjToAdd.addAdj(grid[i][j]);
                
                break;
            case LEFT:
            	// Uses initial to find center boardcell of doorway based on direction
                adjToAdd = roomMap.get(grid[i][j-1].getInitial()).getCenterCell();

                grid[i][j].addAdj(adjToAdd);

                adjToAdd.addAdj(grid[i][j]);
                
                break;
            case RIGHT:
            	// Uses initial to find center boardcell of doorway based on direction
                adjToAdd = roomMap.get(grid[i][j+1].getInitial()).getCenterCell();

                grid[i][j].addAdj(adjToAdd);

                adjToAdd.addAdj(grid[i][j]);

                break;
            default:
                break;
        }
        
    }

    /**
     * Calculate the targets based on a starting cell and path length
     * @param cell
     * @param pathLength
     */
    public void calcTargets(BoardCell startCell, int pathLength) {
        // calculate the available targets
    	targets = new HashSet<BoardCell>();
        visited = new HashSet<BoardCell>();
        visited.add(startCell);
        findTargets(startCell, pathLength);
    }

    /**
     * Recursive helper function to find the targets
     * @param startCell
     * @param pathLength
     */
    private void findTargets(BoardCell startCell, int pathLength) {

        // base case
        if(pathLength == 0) {
        	targets.add(startCell);
        }
        else {
        	// Checks every adjacency possible for all eligible cells recursively 
        	for(BoardCell adjCell: startCell.getAdjList()) {

                // Handles occupied spaces so that they are not ever reached by targeting list
        		if(adjCell.getIsOccupied()) {
        			visited.add(adjCell);   
        			// Ensures that occupied rooms are still accessible 
                    if(adjCell.isRoomCenter()) {
                        targets.add(adjCell);
                    }
        		}
                // Handles new spaces found in the adjacency list
        		if(!visited.contains(adjCell)) {
        			visited.add(adjCell);
        			if(adjCell.isRoomCenter()) {
        				targets.add(adjCell);
        			}
        			else {
        				// Searches through each adjacency of possible places to move recursively
        				findTargets(adjCell, pathLength - 1);
        			}
        			visited.remove(adjCell);
        		}
        	}
        }
    }

    /**
     * Private helper function to populate the grid with the proper BoardCell
     */
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
                    grid[i][j] = new BoardCell(i, j, cells[j].charAt(0));
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
    
    /**
     * Deals the cards to the players
     */
    public void deal() {

        Random rand = new Random();
    	
        // Deal to solution first
        int randRoomIndex = rand.nextInt(7) + 0;
        int randPlayerIndex = rand.nextInt(6) + 9;
        int randWeaponIndex = rand.nextInt(6) + 15;

        solution = new Solution(cards.get(randRoomIndex), cards.get(randPlayerIndex), cards.get(randWeaponIndex));

    	cards.remove(randWeaponIndex);
        cards.remove(randPlayerIndex);
        cards.remove(randRoomIndex);

        // dealin time
        while (cards.size() != 0) {
            for (Player player : players) {
                int randIndex = rand.nextInt(cards.size());
                player.updateHand(cards.get(randIndex));
                cards.remove(randIndex);
            }
        }
        
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
     * Returns the list of targets
     * @return
     */
    public Set<BoardCell> getTargets() {
        return targets;
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
    
    public ArrayList<Player> getPlayers(){
    	return players;
    }
    
    public ArrayList<Card> getCards(){
    	return cards;
    }
    
    public Solution getSolution() {
    	return solution;
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
