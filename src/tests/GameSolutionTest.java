package tests;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
import clueGame.ComputerPlayer;

public class GameSolutionTest {

    private static Board board;
    private static Card room, person, weapon;
    private static Player testPlayer;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static ArrayList<Player> players = new ArrayList<Player>();

    @BeforeAll
    static public void startup() {
        player1 = new HumanPlayer("bob", "blue", 0,0);
        player2 = new ComputerPlayer("dob", "blue", 0,0);
        player3 = new ComputerPlayer("rob", "blue", 0,0);

        // add players to list
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // update each players hand with 3 cards
        player1.updateHand(new Card("Basement", CardType.ROOM));
        player1.updateHand(new Card("Eliza", CardType.PERSON));
        player1.updateHand(new Card("Paper", CardType.WEAPON));

        player2.updateHand(new Card("Office", CardType.ROOM));
        player2.updateHand(new Card("Matt", CardType.PERSON));
        player2.updateHand(new Card("Paper", CardType.WEAPON));

        player3.updateHand(new Card("Living Room", CardType.ROOM));
        player3.updateHand(new Card("Matt", CardType.PERSON));
        player3.updateHand(new Card("Rock", CardType.WEAPON));

    }

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

        Solution query = new Solution(room, person, weapon);

        // Test query no players can disprove
        assertEquals(board.handleSuggestion(player1, query, players), null);
        assertEquals(board.handleSuggestion(player2, query, players), null);
        assertEquals(board.handleSuggestion(player3, query, players), null);

        // Test query that only suggesting player can disprove
        Solution query2 = new Solution(new Card("Basement", CardType.ROOM), new Card("Eliza", CardType.PERSON), new Card("Paper", CardType.WEAPON));
        assertEquals(board.handleSuggestion(player1, query2, players), null);

        // Test query that only human player can disprove
        Solution query3 = new Solution(new Card("Master Bedroom", CardType.ROOM), new Card("Eliza", CardType.PERSON), new Card("Frying Pan", CardType.WEAPON));
        assertEquals(board.handleSuggestion(player2, query3, players), new Card("Eliza", CardType.PERSON));




        
    }
}