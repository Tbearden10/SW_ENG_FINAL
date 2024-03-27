package clueGame;

public class Card {
    private String cardName;

    public Card(String cardName) {
        this.cardName = cardName;
    }

    public boolean equals(Card target) {
        return cardName.equals(target.cardName);
    }
}
