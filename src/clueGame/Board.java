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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.HashSet;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;


public class Board extends JPanel{
    
    private BoardCell[][] grid;

    private int numRows;
    private int numColumns;
    private int currentPlayerIndex;

    private String currentGuessResult;
    private String layoutConfigFile;
    private String setupConfigFile;
    
    public Solution solution;
    private Solution currentSuggestion;

    private ArrayList<Card> dealCards;
    private ArrayList<Card> suggestionCards;
    private ArrayList<Player> players;
    private ArrayList<String[]> boardList; // assists when layout setup

    private Map<Character, Room> roomMap; // holds room cells

    private Set<BoardCell> targets; // holds target cells
    private Set<BoardCell> visited; // holds visited cells

    private boolean isTurnOver = false;
    private boolean isMoved = false;

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

    protected MouseListener boardListener = new MouseListener() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            // is it human player turn
            Player currentPlayer = getCurrentPlayer();
            if (!(currentPlayer instanceof HumanPlayer)) {
                return;
            }
            else {
                // clicked on target (use the mouse event to get position of click)
                int cellSize = Math.min(getWidth() / numColumns, getHeight() / numRows);

                // Calculate the offset from the start of the grid
                int offsetX = (getWidth() - cellSize * numColumns) / 2;
                int offsetY = (getHeight() - cellSize * numRows) / 2;

                // Calculate the row and column indices
                int row = (e.getY() - offsetY) / cellSize;
                int col = (e.getX() - offsetX) / cellSize;

                int moveRow, moveCol;

                // Ensure that the click is within the bounds of the grid
                if (row < 0 || row >= numRows || col < 0 || col >= numColumns) {
                    // Click is out of bounds
                    return;
                }

                // clicked on target
                BoardCell cell = grid[row][col];

                if (targets.contains(roomMap.get(grid[row][col].getInitial()).getCenterCell())) {
                    moveRow = roomMap.get(grid[row][col].getInitial()).getCenterCell().getRow();
                    moveCol = roomMap.get(grid[row][col].getInitial()).getCenterCell().getCol();
                    cell = roomMap.get(grid[row][col].getInitial()).getCenterCell();
                }
                else {
                    moveRow = row;
                    moveCol = col;
                }

                if (targets.contains(cell)) {
                    // move player
                    if (isMoved == false) {
                        if (cell.isRoomCenter() && cell.getIsOccupied()) {
                            currentPlayer.doMove(moveRow, moveCol, 20);
                        }
                        else {
                            currentPlayer.doMove(moveRow, moveCol, 0);
                        }
                        
                        setTurnOver(true);
                        isMoved = true;
                        setTargetsVisible(false);
                    }
                    
                    

                    // in room ?
                    if (cell.isRoomCenter()) {
                        
                        Room room = getRoom(cell.getInitial());

                        GameControlPanel controlPanel = GameControlPanel.getInstance();

                        // get list of player names
                        ArrayList<String> playerNames = new ArrayList<String>();
                        for (Player player : getPlayers()) {
                            playerNames.add(player.getName());
                        }

                        // get list of weapon names
                        ArrayList<String> weaponNames = new ArrayList<String>();
                        for (Card card : getWeapons()) {
                            weaponNames.add(card.getCardName());
                        }
                        
                        getWeapons();

                        // create dialog box java
                        JDialog suggestionDialog = new JDialog();
                        suggestionDialog.setLayout(new GridLayout(1, 2));
                        suggestionDialog.setSize(300, 200);
                        suggestionDialog.setVisible(true);


                        // add labels
                        JLabel roomLabel = new JLabel("Current room");
                        JLabel playerLabel = new JLabel("Person");
                        JLabel weaponLabel = new JLabel("Weapon");

                        // add jtext field for room
                        JTextField roomTextField = new JTextField(room.getName());
                        roomTextField.setEditable(false);

                        // add drop down to select player
                        JComboBox<String> playerComboBox = new JComboBox<String>(playerNames.toArray(new String[playerNames.size()]));

                        // add drop down to select weapon
                        JComboBox<String> weaponComboBox = new JComboBox<String>(weaponNames.toArray(new String[weaponNames.size()]));

                        

                        // add labels and submit button on left column
                        JPanel leftPanel = new JPanel();
                        leftPanel.setLayout(new GridLayout(4, 1));
                        leftPanel.add(roomLabel);
                        leftPanel.add(playerLabel);
                        leftPanel.add(weaponLabel);
                        // submit button
                        JButton submitButton = new JButton("Submit");
                        leftPanel.add(submitButton);

                        // add textfield, dropdowns, and cancel button to right
                        JPanel rightPanel = new JPanel();
                        rightPanel.setLayout(new GridLayout(4, 1));
                        rightPanel.add(roomTextField);
                        rightPanel.add(playerComboBox);
                        rightPanel.add(weaponComboBox);
                        // cancel button
                        JButton cancelButton = new JButton("Cancel");
                        rightPanel.add(cancelButton);

                        // add panels to dialog
                        suggestionDialog.add(leftPanel);
                        suggestionDialog.add(rightPanel);

                        // use cancel button to close dialog
                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                suggestionDialog.dispose();
                            }
                        });

                        // use submit button to submit suggestion
                        submitButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                suggestionDialog.dispose();

                                String playerName = String.valueOf(playerComboBox.getSelectedItem());
                                String weaponName = String.valueOf(weaponComboBox.getSelectedItem());
                                String roomName = room.getName();

                                Card roomCard = new Card(roomName, CardType.ROOM);
                                Card playerCard = new Card(playerName, CardType.PERSON);
                                Card weaponCard = new Card(weaponName, CardType.WEAPON);

                                System.out.println(playerName);

                                Solution suggSolution = new Solution(roomCard, playerCard, weaponCard);
                                
                                // HANDLE SUGGESTION
                                Card card = handleSuggestion(getCurrentPlayer(), suggSolution, getPlayers());

                                if (card != null) {
                                    controlPanel.guessResultTextField.setText(card.getCardName());
                                    for (Player player : getPlayers()) {
                                        if (player.getCards().contains(card)) {
                                            controlPanel.guessResultTextField.setBackground(Color.decode((player.getColor())));
                                        }
                                    }
                                }
                                else {
                                    controlPanel.guessResultTextField.setText("No new clue");

                                }

                                controlPanel.guessTextField.setText(suggSolution.toString());
                                controlPanel.guessTextField.setBackground(Color.decode(currentPlayer.getColor()));

                            }
                        });

                        
                    }
                    



                }
                else {
                    // clicked on non-target
                    JOptionPane message = new JOptionPane();
                    JOptionPane.showMessageDialog(message, "You must select a correct location.", "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

        }


        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {}

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {}
    };


    /**
     * Reads in the setup file to handle the room types 
     * Handles any errors that may be found in the setup file
     * @throws FileNotFoundException
     * @throws BadConfigFormatException
     */
    public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
        roomMap = new HashMap<Character, Room>();
        dealCards = new ArrayList<Card>();
        suggestionCards = new ArrayList<Card>();
        players = new ArrayList<Player>();
        File setupFile = new File(setupConfigFile);
        
       
        Scanner fileScanner = new Scanner(setupFile);

        while (fileScanner.hasNextLine()) {
            String fullString = fileScanner.nextLine();
            String[] line = fullString.split(", ");
            switch (line[0]) {
                case "Room": {
                    roomMap.put(line[2].charAt(0), new Room(line[1]));
                    dealCards.add(new Card(line[1], CardType.ROOM));
                    break;
                }
                case "Player": {
                    if (line[1].equals("Human")) {
                        players.add(new HumanPlayer(line[2], line[3], Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                    }
                    else if (line[1].equals("Computer")){
                        players.add(new ComputerPlayer(line[2], line[3], Integer.parseInt(line[4]), Integer.parseInt(line[5])));
                    }
                    dealCards.add(new Card(line[2], CardType.PERSON));
                    suggestionCards.add(new Card(line[2], CardType.PERSON));
                    break;
                }
                case "Weapon": {
                    dealCards.add(new Card(line[1], CardType.WEAPON));
                    suggestionCards.add(new Card(line[1], CardType.WEAPON));
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
        
        
        @SuppressWarnings("resource")
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
     * Calculates the adjacencies for each cell
     * @param grid
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

        // base case 1
        if (startCell.isRoomCenter()) {
            targets.add(startCell);
        }
        // base case 2
        if (pathLength == 0) {
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
       // random index between 0 and 8
        int randRoomIndex = rand.nextInt(9);
        // random index between 9 and 14
        int randPlayerIndex = rand.nextInt(6) + 9;
        // random index between 15 and 20
        int randWeaponIndex = rand.nextInt(6) + 15;

        solution = new Solution(dealCards.get(randRoomIndex), dealCards.get(randPlayerIndex), dealCards.get(randWeaponIndex));

    	dealCards.remove(randWeaponIndex);
        dealCards.remove(randPlayerIndex);
        dealCards.remove(randRoomIndex);

        // dealin time
        while (dealCards.size() != 0) {
            for (Player player : players) {
                int randIndex = rand.nextInt(dealCards.size());
                Card card = dealCards.get(randIndex);
                card.setColor(player.getColor());
                player.updateHand(card);
                dealCards.remove(randIndex);
            }
        }
        
    }

    /**
     * Return whether the accusation is correct
     * @return 
     */
    public boolean checkAccusation(Solution accusation) {

        if (solution.getPerson() ==  accusation.getPerson()) {
            if (solution.getRoom() == accusation.getRoom()) {
                if (solution.getWeapon() == accusation.getWeapon()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Return the first card that disputes the suggestion
     * @param suggestion
     * @return
     */
    public Card handleSuggestion(Player accuser, Solution suggestion, ArrayList<Player> players) {
    	
    	Set<Card> seenCards = new HashSet<Card>();
    	seenCards = accuser.getSeenCards();

        // move the suggested player to the accuser room
        for (Player player : players) {
            if (player.getName().equals(suggestion.getPerson().getCardName())) {

                System.out.println("Player: " + player.getName() + " is being moved to the accuser room");
                // print accuser
                System.out.println("Accuser: " + accuser.getName());

                if (grid[accuser.getRow()][accuser.getCol()].isRoomCenter() && grid[accuser.getRow()][accuser.getCol()].getIsOccupied()) {
                    player.doMove(accuser.getRow(), accuser.getCol(), 20);
                }   
                else {
                    player.doMove(accuser.getRow(), accuser.getCol(), 0);
                }
            }
        }
    	
    	// checks if the player is the accuser, disproves suggestion, and adds card to the seen cards
        for (Player player : players) {
            if (player.equals(accuser)) {
                continue;
            }
            else {
                
            	Card card = player.disproveSuggestion(suggestion);
                if (card != null) {
                	seenCards.add(card);
                	accuser.setSeenCards(seenCards);
                    return card;
                }
            }	
        }

        accuser.setAccusationFlag(true);
        return null;
    }

    /**
     * Set the targets visible
     * @param visible
     */
    public void setTargetsVisible(boolean visible) {
         
        if (visible == false) {
            // set all cells to not visible
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    grid[i][j].setIsVisible(false);
                }
            }
        }
        

        targets.forEach(cell -> cell.setIsVisible(visible));
    }

    /**
     * Paints the board
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // do some painting
        int cellWidth = this.getWidth();
        int cellHeight = this.getHeight();

        int cellSize = Math.min(cellWidth / numColumns, cellHeight / numRows);
        
        int totalWidth = cellSize * numColumns;
        int totalHeight = cellSize * numRows;
        
        int startX = (cellWidth - totalWidth) / 2;
        int startY = (cellHeight - totalHeight) / 2;
        
        int cellXPos = 0, cellYPos = 0;
        
        // draw the board
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
            	cellXPos = startX + j * cellSize;
                cellYPos = startY + i * cellSize;
                // get isTarget
                boolean isTarget = getTargets() != null && getTargets().contains(grid[i][j]);
                
                if (getTargets() != null && getTargets().contains(roomMap.get(grid[i][j].getInitial()).getCenterCell())) {
                    grid[i][j].draw(g, cellSize, cellSize, cellXPos, cellYPos, true);

                    if (getCurrentPlayer() instanceof HumanPlayer && isMoved == false) {
                        grid[i][j].setIsVisible(true);
                    }
                }
                else {
                    grid[i][j].draw(g, cellSize, cellSize, cellXPos, cellYPos, isTarget);
                }


                cellXPos = (int) (cellXPos + (cellWidth / numColumns));

                if (grid[i][j].isRoomCenter()) {
                    String name = "";
                    String[] split = new String[0];
                    for (Map.Entry<Character, Room> entry : roomMap.entrySet()) {
                        if (entry.getKey() == grid[i][j].getInitial()) {
                            name = entry.getValue().getName();
                            split = name.split(" ");
                            // add \n char if there is a space
                        }
                    }
                    g.setColor(Color.RED);
                    Font font = new Font("Harlow Solid Italic", Font.ITALIC, cellWidth/55);
                    g.setFont(font);
                    
                    int tempX = cellXPos;
                    if (grid[i][j].getInitial() != 'S') {
                        tempX = (int) (cellXPos - (cellWidth / numColumns));
                    }

                    if (split.length > 1) {
                        g.drawString(split[0], tempX - ((int) (cellWidth / numColumns)), cellYPos - ((int) (cellHeight / numRows)));
                        g.drawString(split[1], tempX - ((int) (cellWidth / numColumns)), 20 + cellYPos - ((int) (cellHeight / numRows)));
                    }
                    else {
                        g.drawString(name, tempX - ((int) (cellWidth / numColumns)), cellYPos - ((int) (cellHeight / numRows)));
                    }
                }
            }
            cellYPos = (int) (cellYPos + (cellHeight / numRows));

            Image background = Toolkit.getDefaultToolkit().createImage("data/R.jpg");
            g.drawImage(background, cellXPos, cellYPos, theInstance);
        }
        
        for (Player player : players) {
        	int playerRow = player.getRow();
        	int playerCol = player.getCol();
        	
        	int playerXPos = startX + playerCol * cellSize;
        	int playerYPos = startY + playerRow * cellSize;     
            
            player.draw(g, playerXPos, playerYPos, cellSize);
        }
        
        repaint();
        revalidate();
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
     * Select random cell from target list
     * @return
     */
    public BoardCell selectTarget() {
        Random rand = new Random();
        int randIndex = rand.nextInt(targets.size());
        int i = 0;
        for (BoardCell cell : targets) {
            if (i == randIndex) {
                return cell;
            }
            i++;
        }
        return null;
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
     * get the player lsit
     * @return
     */
    public ArrayList<Player> getPlayers(){
    	return players;
    }
    
    /**
     * get the deal cards
     * @return
     */
    public ArrayList<Card> getDealCards(){
    	return dealCards;
    }

    public ArrayList<Card> getWeapons() {
        ArrayList<Card> weapons = new ArrayList<Card>();
        for (Card card : suggestionCards) {
            if (card.getCardType() == CardType.WEAPON) {
                weapons.add(card);
            }
        }
        return weapons;
    }

    /**
     * get the human player
     * @return
     */
    public HumanPlayer getHumanPlayer() {
        for (Player player : players) {
            if (player instanceof HumanPlayer) {
                return (HumanPlayer) player;
            }
        }
        return null;
    }

    /**
     * set the next player index
     */
    public void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % (players.size());
	}

    /**
     * get the current player
     * @return
     */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

    /**
     * get is turn over
     * @return
     */
    public boolean isTurnOver() {
        return isTurnOver;
    }

    /**
     * set turn over
     * @param isTurnOver
     */
    public void setTurnOver(boolean isTurnOver) {
        this.isTurnOver = isTurnOver;
    }

    /**
     * get is moved
     * @return
     */
    public boolean getIsMoved() {
        return isMoved;
    }

    /**
     * set is moved
     * @param isMoved
     */
    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }
    
    /**
     * get the suggestion cards
     * @return
     */
    public ArrayList<Card> getSuggestionCards(){
    	return suggestionCards;
    }
    
    /**
     * get the solution
     * @return
     */
    public Solution getSolution() {
    	return solution;
    }

    /**
     * set the solution
     * @param solution
     */
    public void setSolution(Solution solution) {
    	this.solution = solution;
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

    /**
     * Set the current player guess
     * @param guess
     */
    public void setCurrentPlayerGuess(Solution guess) {
        this.currentSuggestion = guess;
    }

    /**
     * Get the current player guess
     * @param result
     * @return
     */
    public Solution getCurrrentSolution() {
        return this.currentSuggestion;
    }


    /**
     * Set the current player guess result
     * @param disproveCard
     */
    public void setCurrentPlayerGuessResult(Card disproveCard) {
        this.currentGuessResult = disproveCard.getCardName();
    }

    /**
     * Get the current player guess result
     * @return
     */
    public String getCurrentPlayerGuessResult() {
        return this.currentGuessResult;
    }
}