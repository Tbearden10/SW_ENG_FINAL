package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class ComputerAITest {

    private static Board board;
    private static Card room, person, weapon;
    private static Player testPlayer;
    private static HumanPlayer player1;
    private static ComputerPlayer player2;
    private static ComputerPlayer player3;
    private static ArrayList<Player> players = new ArrayList<Player>();

    @BeforeAll
    static public void startup() {
        player1 = new HumanPlayer("bob", "blue", 23,20);
        player2 = new ComputerPlayer("dob", "red", 23,20);
        player3 = new ComputerPlayer("rob", "green", 0,0);

        // update each players hand with 3 cards
        player1.updateHand(new Card("Basement", CardType.ROOM));
        player1.updateHand(new Card("Eliza", CardType.PERSON));
        player1.updateHand(new Card("Paper", CardType.WEAPON));

        player2.updateHand(new Card("Office", CardType.ROOM));
        player2.updateHand(new Card("Matt", CardType.PERSON));
        player2.updateHand(new Card("Frying Pan", CardType.WEAPON));

        player3.updateHand(new Card("Living Room", CardType.ROOM));
        player3.updateHand(new Card("Jon", CardType.PERSON));
        player3.updateHand(new Card("Gun", CardType.WEAPON));
        
        // add players to list
        players.add(player1);
        players.add(player2);
        players.add(player3);

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
    public void testComputerSuggestions() {
    	// ensures that the room is where the player is. 
    	assertEquals(player2.createSuggestion(player2, board).getRoom().getCardName(), "Master Bedroom");
    	
    	Set<Card> seenCardsP2 = player2.getSeenCards();
    	for(Card suggestion: board.getSuggestionCards()) {
    		if(suggestion.getCardName().equals("Bomb")) {
    			continue;
    		}
    		else if(suggestion.getCardName().equals("Eliza")) {
    			continue;
    		}
    		else {
    			seenCardsP2.add(suggestion);
    		}
    	}
    	
    	// Tests if either five players or weapons are seen and the last is guaranteed to be chosen
    	assertEquals(player2.createSuggestion(player2, board).getWeapon().getCardName(), "Bomb");
    	assertEquals(player2.createSuggestion(player2, board).getPerson().getCardName(), "Eliza");
    	
    	seenCardsP2.clear();
    	
    	// Test if the cards are random for the suggestion given that five of either the player or weapon aren't seen
    	assertEquals(player2.createSuggestion(player2, board).getWeapon().getCardType(), CardType.WEAPON);
    	assertEquals(player2.createSuggestion(player2, board).getPerson().getCardType(), CardType.PERSON);
    	assert(board.getSuggestionCards().contains(player2.createSuggestion(player2, board).getPerson()));
    	assert(board.getSuggestionCards().contains(player2.createSuggestion(player2, board).getWeapon()));
    }
}
