package tests;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Set;

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
    private static ComputerPlayer player;
    private static ArrayList<Player> players = new ArrayList<Player>();

    @BeforeAll
    static public void startup() {
        player = new ComputerPlayer("dob", "red", 23,20);
       
        player.updateHand(new Card("Office", CardType.ROOM));
        player.updateHand(new Card("Matt", CardType.PERSON));
        player.updateHand(new Card("Frying Pan", CardType.WEAPON));

     
        players.add(player);
     

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
    	assertEquals(player.createSuggestion(player, board).getRoom().getCardName(), "Master Bedroom");
    	
    	Set<Card> seenCardsP2 = player.getSeenCards();
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
    	assertEquals(player.createSuggestion(player, board).getWeapon().getCardName(), "Bomb");
    	assertEquals(player.createSuggestion(player, board).getPerson().getCardName(), "Eliza");
    	
    	seenCardsP2.clear();
    	
    	// Test if the cards are random for the suggestion given that five of either the player or weapon aren't seen
    	assertEquals(player.createSuggestion(player, board).getWeapon().getCardType(), CardType.WEAPON);
    	assertEquals(player.createSuggestion(player, board).getPerson().getCardType(), CardType.PERSON);
    	assert(board.getSuggestionCards().contains(player.createSuggestion(player, board).getPerson()));
    	assert(board.getSuggestionCards().contains(player.createSuggestion(player, board).getWeapon()));
    }

    @Test
    public void testComputerTargetSelection() {        
    	// test if no rooms in target list, select randomly
        
    
        // if room in seen list that has not been seen, select that room

        // if room in seen list that has been seen, select randomly
    }
}
