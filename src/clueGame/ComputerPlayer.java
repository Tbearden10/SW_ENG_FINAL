/*
 * Authors Brayden Clark and Tanner Bearden
 * Date: 3/29/2024
 * 
 * Collaborators: None
 * 
 * 
 * This class is used to represent a computer player in the game of Clue.
 * It extends the Player class and adds additional functionality
 */
package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	ArrayList<Card> suggestions = new ArrayList<Card>();
	Set<Card> seenCards = new HashSet<Card>();
	Set<Character> previousRooms = new HashSet<Character>();
	
    public ComputerPlayer() {
        super();
    }

    public ComputerPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
		super.previousRooms = new HashSet<Character>();
    }
    
    public Solution createSuggestion(Player player, Board board){
    	suggestions = board.getSuggestionCards();
    	seenCards = player.getSeenCards();
    	Card weapon = null;
    	Card person = null;
    	int seenWeapons = 0;
    	int seenPlayers = 0;
    	Random rand = new Random();
    	int randPlayerIndex = rand.nextInt(6);
    	int randWeaponIndex = rand.nextInt(6) + 6;
    	String room = board.getRoom(board.getCell(player.getRow(), player.getCol())).getName();
    	
    	// loading suggestions to see how many cards player has identified
    	for(Card card : suggestions) {
    		if(seenCards.contains(card) && card.getCardType().equals(CardType.PERSON)) {
    			seenPlayers++;
    		}
    		if(seenCards.contains(card) && card.getCardType().equals(CardType.WEAPON)) {
    			seenWeapons++;
    		}
    	}
    	
    	// ensuring that the play picks the single left out card if five of the weapons are found
    	if(seenWeapons == 5) {
    		for(Card suggestion : suggestions) {
    			if(seenCards.contains(suggestion)) {
    				continue;
    			}
    			else if(suggestion.getCardType() == CardType.WEAPON){
    				weapon = suggestion;
    			}
    		}
    	}
    	else {
    		weapon = suggestions.get(randWeaponIndex);
    	}
    	
    	// ensuring that the play picks the single left out card if five of the players are found
    	if(seenPlayers == 5) {
    		for(Card suggestion : suggestions) {
    			if(seenCards.contains(suggestion)) {
    				continue;
    			}
    			else if(suggestion.getCardType() == CardType.PERSON){
    				person = suggestion;
    			}
    		}
    	}
    	else {
    		person = suggestions.get(randPlayerIndex);
    	}
    	
    	return new Solution(new Card(room, CardType.ROOM), person, weapon);
    }

	/**
	 * Private helper function to determine if the room has been visited before
	 * @param room
	 * @return
	 */
	private boolean isPreviousRoom(char room) {
		return previousRooms.contains(room);
	}


	/**
	 * Move to the desired target location
	 * @param target
	 */
	public void doMove(BoardCell target) {
		// moves the player to the target location
		this.row = target.getRow();
		this.col = target.getCol();
		if (target.isRoomCenter()) {
			super.previousRooms.add(target.getInitial());
		}
	}

	/**
	 * Selects a random target from the set of targets
	 * @param targets
	 */
	private BoardCell getRandomTarget(Set<BoardCell> targets) {
		// selects a random target from the set of targets
		Random rand = new Random();
		int index = rand.nextInt(targets.size());
		int i = 0;
		for (BoardCell target : targets) {
			if (i == index) {
				return target;
			}
			i++;
		}
		return null;
	}

	public BoardCell selectMoveTarget(Set<BoardCell> targets) {

		Set<BoardCell> roomTargets = new HashSet<BoardCell>();

		// checks if the target is a room center and if the room has been visited before
		for (BoardCell cell : targets) {
			if ((cell.isRoomCenter() || cell.isDoorway()) && !isPreviousRoom(cell.getInitial())) {
				if (!cell.isDoorway()) {
					roomTargets.add(cell);
				}
			}
		}

		// if there are room targets, then the player will move to the room target
		if (!roomTargets.isEmpty()) {
			return getRandomTarget(roomTargets);
		}


		// if there are no room targets, then the player will move to a random target
		if (!targets.isEmpty()) {
			return getRandomTarget(targets);
		}

		return new BoardCell(0, 0, ' ');
	}
}
