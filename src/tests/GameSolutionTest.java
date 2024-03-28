package tests;


import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Solution;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.HumanPlayer;

public class GameSolutionTest {

    private static Board board;
    private static Card room, person, weapon;
    private static Player testPlayer;

    @BeforeEach
    public void setup() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();

        room = new Card("Kitchen", CardType.ROOM);
        person = new Card("Eduard", CardType.PERSON);
        weapon = new Card("Bomb", CardType.WEAPON);
    }

    @Test
    public void testCorrectSolution() {
        board.deal();

        

        // create and set testSolution
        Solution testSolution = new Solution(room, person, weapon);
        board.setSolution(testSolution);

        // check if solution is correct
        assertEquals(board.checkAccusation(testSolution), true);
    }

    @Test
    public void testIncorrectSolution() {
        board.deal();

        // check if solution is incorrect
        assertEquals(board.checkAccusation(new Solution(room, person, weapon)), false);
    }

    @Test
    public void testDisproveSuggestion() {

        // test with one matching card
        testPlayer = new HumanPlayer("Joe", "Red", 0, 0);
        testPlayer.updateHand(room);
        testPlayer.updateHand(person);
        testPlayer.updateHand(weapon);

        // create set of cards to test 
        Solution oneMatchingSolution = new Solution(room, new Card("Eliza", CardType.PERSON), new Card("Rock", CardType.WEAPON));

        // test matching room
        assertEquals(testPlayer.disproveSuggestion(oneMatchingSolution), room);

        // ==================================

        // test with no matching cards
        testPlayer = new HumanPlayer("Tanner", "Blue", 0, 0);
        testPlayer.updateHand(room);
        testPlayer.updateHand(person);
        testPlayer.updateHand(weapon);

        Solution noMatchingSolution = new Solution(new Card("Office", CardType.ROOM), new Card("Matt", CardType.PERSON), new Card("Paper", CardType.WEAPON));

        // test no matching cards
        assertEquals(testPlayer.disproveSuggestion(noMatchingSolution), null);
    
        //===================================

        // test with multiple matching card
        testPlayer = new HumanPlayer("William", "Blue", 0, 0);
        testPlayer.updateHand(room);
        testPlayer.updateHand(person);
        testPlayer.updateHand(weapon);

        Solution allMatchingSolution = new Solution(room, person, weapon);

        Card returnCard = testPlayer.disproveSuggestion(allMatchingSolution);

        Card matchingCard;

        if (allMatchingSolution.getRoom().equals(returnCard)) {
            matchingCard = allMatchingSolution.getRoom();
            assertEquals(returnCard, matchingCard);
        }

        if (allMatchingSolution.getPerson().equals(returnCard)) {
            matchingCard = allMatchingSolution.getPerson();
            assertEquals(returnCard, matchingCard);
        }

        if (allMatchingSolution.getWeapon().equals(returnCard)) {
            matchingCard = allMatchingSolution.getWeapon();
            assertEquals(returnCard, matchingCard);           
        }
    }

    @Test
    public void testHandleSuggestion() {

        board.deal();

        
        // Test query no players can disprove
    

        // Test query that only suggesting player can disprove

        
    }
}