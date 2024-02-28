package clueGame;

import java.util.Map;

public class Board {
    
    private BoardCell[][] grid;

    private int numRows;

    private int numColumns;

    private String layoutConfigFile;

    private String setupConfigFile;

    private Map<Character, Room> roomMap;

    private static Board theInstance = new Board();

    private Board() {
        super();
    }

    public static Board getInstance() {
        return theInstance;
    }

    public void initialize() {
        
    }

    public BoardCell getCell(int row, int col) {
        return new BoardCell(row, col);
    }

    public Room getRoom(char c) {
        return new Room("");
    }

    public Room getRoom(BoardCell cell) {
        return new Room("");
    }

    public void loadSetupConfig() {

    }

    public void loadLayoutConfig() {

    }

    public int getNumRows() {
        return 0;
    }

    public int getNumColumns() {
        return 0;
    }

	public void setConfigFiles(String layout, String setup) {
		
	}

}
