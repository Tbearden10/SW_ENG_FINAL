package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

abstract public class Player {
    private String name;

    private String color; 
    
    private ArrayList<Card> cards;
    
    private int row, col;
    
    private Set<Card> seenCards;

    public Player() {
        super();
    }

    /**
     * Constructor
     * @param name
     * @param color
     * @param row
     * @param col
     */
    public Player(String name, String color, int row, int col) {
    	seenCards = new HashSet<Card>();
        cards = new ArrayList<Card>();
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }

    /**
     * Updates the player's hand by adding a card
     * @param card
     */
    public void updateHand(Card card) {
        cards.add(card);
    }

    /**
     * Method to handle player disproving a suggestion
     * @param suggestion
     * @return
     */
    public Card disproveSuggestion(Solution suggestion) {
       
        ArrayList<Card> matchingCards = new ArrayList<Card>();
        for (Card card : cards) {
            if (card.equals(suggestion.getRoom()) || card.equals(suggestion.getPerson()) || card.equals(suggestion.getWeapon())) {
                matchingCards.add(card);
            }
        }
        if (matchingCards.size() == 0) {
            return null;
        }
        return matchingCards.get((int) (Math.random() * matchingCards.size()));
    }
    
    public boolean equals(Player target) {
    	return target.name.equals(name);
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

    public String getColor() {
        return color;
    }
    
    public Set<Card> getSeenCards(){
    	return seenCards;
    }

	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}
    
    
    
}
