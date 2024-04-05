package clueGame;

public class Card {
    private String cardName;
    private CardType cardType;
    private String color;

    public Card(String cardName, CardType cardType) {
        this.cardName = cardName;
        this.cardType = cardType;
    }


    public boolean equals(Card target) {
        return cardName.equals(target.cardName);
    }
    
    public String getCardName() {
    	return cardName;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }
}
