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

	/**
	 * Constructor
	 * @param name
	 * @param color
	 * @param row
	 * @param col
	 */
    public ComputerPlayer(String name, String color, int row, int col) {
        super(name, color, row, col);
		super.previousRooms = new HashSet<Character>();
    }
    
	/**
	 * Selects a target location to move to
	 * @param player
	 * @param board
	 * @return
	 */
    public Solution createSuggestion(Player player, Board board){
    	suggestions = board.getSuggestionCards();
    	seenCards = player.getSeenCards();

    	Card weapon = null;
    	Card person = null;

    	Random rand = new Random();
    	int seenWeapons = 0;
    	int seenPlayers = 0;
    	int randPlayerIndex = rand.nextInt(6);
    	int randWeaponIndex = rand.nextInt(6) + 6;

    	String room = board.getRoom(board.getCell(player.getRow(), player.getCol())).getName();
    	
    	// loading suggestions to see how many cards player has identified
    	for(Card card : suggestions) {

			if (seenCards.contains(card)) {
				switch (card.getCardType()) {
					case CardType.PERSON:
						seenPlayers++;
						break;
					case CardType.WEAPON:
						seenWeapons++;
						break;
					default:
						break;
				}
			}
    	}
    	

    	weapon = handleSingleLeftOuCard(seenWeapons, suggestions, seenCards, CardType.WEAPON, randWeaponIndex);
    	person = handleSingleLeftOuCard(seenPlayers, suggestions, seenCards, CardType.PERSON, randPlayerIndex);
    	
    	return new Solution(new Card(room, CardType.ROOM), person, weapon);
    }

	private Card handleSingleLeftOuCard(int seenCount, ArrayList<Card> suggestionCards, Set<Card> seenCards, CardType cardType, int randIndex) {
		Card card = null;
		if(seenCount == 5) {
			for(Card suggestion : suggestionCards) {
				if(seenCards.contains(suggestion)) {
					continue;
				}
				else if(suggestion.getCardType() == cardType){
					card = suggestion;
				}
			}
		}
		else {
			card = suggestionCards.get(randIndex);
		}
		return card;
		
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
				if (!target.getIsOccupied()) {
					return target;
				}
			}
			i++;
		}
		return null;
	}

	/**
	 * Selects the target location to move to
	 * @param targets
	 * @return
	 */
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
