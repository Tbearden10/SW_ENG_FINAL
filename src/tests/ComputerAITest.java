/*
 * Authors Brayden Clark and Tanner Bearden
 * Date: 3/29/2024
 * 
 * Collaborators: None
 * 
 * 
 * Description: This class tests the functionality of the computer player AI
 
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class ComputerAITest {

    private static Board board;
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

        // test a list of targets in which contain unvisited rooms
        BoardCell testCell = board.getCell(20, 17);
        board.calcTargets(testCell, 5);
        Set<BoardCell> targets = board.getTargets();
        assertTrue(targets.contains(player.selectMoveTarget(targets)));

        // test list of targets with a visited room
        targets.clear();
        player.addPreviousRoom('M');
        board.calcTargets(testCell, 5);
        targets = board.getTargets();
        assertTrue(targets.contains(player.selectMoveTarget(targets)));
        
       
        // random list of targets in which are NOT rooms
        targets.clear();
        targets.add(new BoardCell(0, 0, ' '));
        targets.add(new BoardCell(0, 1, ' '));
        assertTrue(targets.contains(player.selectMoveTarget(targets)));
    }
}
