package tests;


import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Solution;
import clueGame.Card;
import clueGame.CardType;

public class GameSolutionTest {

    private static Board board;

    @BeforeEach
    public void setup() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    @Test
    public void testCorrectSolution() {
        board.deal();

        Card room = new Card("Kitchen", CardType.ROOM);
        Card person = new Card("Eduard", CardType.PERSON);
        Card weapon = new Card("Bomb", CardType.WEAPON);

        // create and set testSolution
        Solution testSolution = new Solution(room, person, weapon);
        board.setSolution(testSolution);

        // check if solution is correct
        assertEquals(board.checkAccusation(testSolution), true);
    }

    @Test
    public void testIncorrectSolution() {
        board.deal();

        Card room = new Card("Kitchen", CardType.ROOM);
        Card person = new Card("Eduard", CardType.PERSON);
        Card weapon = new Card("Bomb", CardType.WEAPON);

        // check if solution is incorrect
        assertEquals(board.checkAccusation(new Solution(room, person, weapon)), false);
    }
}