import java.util.*;

public class Player {

    private int id, balance, currentPosition;
    private String name;
    private boolean isTheirturn , isMoneyDeposited, isInJail;
    private Vector<Lands> ownLands = new Vector<Lands>();

    public Player(int id, String name) {
        //TODO
    }

    public void move(int diceRoll){
        //TODO
    }

    public boolean buyProperty(){
        //TODO
        return false;
    }

    public boolean payRent(){
        //TODO
        return false;
    }

    public boolean equals(Player player) {
        return this.id==player.id;
    }
}
