package clueGame;

public class Solution {
    private Card room;

    private Card person;

    private Card weapon;

    public Solution(Card room, Card person, Card weapon) {
        this.room = room;
        this.person = person;
        this.weapon = weapon;
    }

	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}
    
	@Override
	public String toString() {
		return "Solution [room=" + room.getCardName() + ", person=" + person.getCardName() + ", weapon=" + weapon.getCardName() + "]";
	}
}
