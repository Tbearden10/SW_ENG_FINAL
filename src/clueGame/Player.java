package clueGame;

abstract public class Player {
    private String name;

    private String color; 

    private int row, col;

    public Player() {
        super();
    }

    public Player(String name, String color, int row, int col) {
        this.name = name;
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public void updateHand(Card card) {
        // do something
    }
}
