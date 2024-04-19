package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Image;
import javax.swing.ImageIcon;

abstract public class Player {
    private String name;
    private String color; 
    
    private ArrayList<Card> cards;
    
    protected int row;
    protected int col;
    protected int offset;
    
    private Set<Card> seenCards;
    protected Set<Character> previousRooms;

    boolean accusationFlag;

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
        this.offset = 0;
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

    /**
     * Method to make a suggestion
     */
    public void makeAccusation() {
        // check if in room
        
    }

    /**
     * Method to roll the die
     * @return
     */
    public int rollDie() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Method to add previous room
     * @param room
     */
    public void addPreviousRoom(Character room) {
        this.previousRooms.add(room);
    }
    
    /**
     * Method to check if player has been in room
     * @param target
     * @return
     */
    public boolean equals(Player target) {
    	return target.name.equals(name);
    }
    
    /**
     * Method to get the player's cards
     * @return
     */
    public ArrayList<Card> getCards() {
    	return cards;
    }

    /**
     * Method to get the player's name
     * @return
     */
	public String getName() {
		return name;
	}

    /**
     * Method to get the player's row and column
     * @return
     */
	public int getRow() {
		return row;
	}

    /**
     * Method to get the player's column
     * @return
     */
	public int getCol() {
		return col;
	}

    /**
     * Method to get the player's color
     * @return
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Method to get the player's seen cards
     * @return
     */
    public Set<Card> getSeenCards(){
    	return seenCards;
    }

    /**
     * Method to set the player's seen cards
     * @param seenCards
     */
	public void setSeenCards(Set<Card> seenCards) {
		this.seenCards = seenCards;
	}

    /**
     * Method to get the player's previous rooms
     * @param seen
     */
    public void setSeen(ArrayList<Card> seen) {
        for (Card card : seen) {
            seenCards.add(card);
        }
    }
    
    /**
     * Method to get the player's previous rooms
     * @param row
     * @param col
     */
    public void doMove(int row, int col, int offset) {
        this.row = row;
        this.col = col;
        this.offset = offset;
    }

    /**
     * Method to set the accusation flag
     * @param flag
     */
    public void setAccusationFlag(boolean flag) {
        this.accusationFlag = flag;
    }

    /**
     * Method to get the accusation flag
     * @return
     */
    public boolean getAccusationFlag() {
        return accusationFlag;
    }

    /**
     * Method to draw the player
     * @param g
     * @param width
     * @param height
     */
    public void draw(Graphics g, int x, int y, int size) {
        Color color = Color.decode(this.color);
        g.setColor(color);
        g.fillOval(x + offset, y, size, size);
        //Graphics2D g2d = (Graphics2D) g.create();

        // Create oval clipping area
        //Ellipse2D.Double oval = new Ellipse2D.Double(x, y, size, size);
        //g2d.setClip(oval);

        // Draw player image
        //g2d.drawImage(playerImage, x, y, size, size, null);

        // Dispose of graphics context
        //g2d.dispose();
    }

    @Override
    public String toString() {
        return "Player [name=" + name + ", color=" + color + ", row=" + row + ", col=" + col + "]";
    }

    
    
}
