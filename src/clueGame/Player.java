package clueGame;

import java.util.ArrayList;

abstract public class Player {
    private String name;

    private String color; 
    
    private ArrayList<Card> cards;
    
    private int row, col;

    public Player() {
        super();
    }

    public Player(String name, String color, int row, int col) {
        cards = new ArrayList<Card>();
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public void updateHand(Card card) {
        cards.add(card);
    }
    
    public ArrayList<Card> getCards() {
    	return cards;
    }

	public String getName() {
		return name;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
    
    
}
