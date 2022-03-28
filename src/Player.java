import java.util.*;

public class Player {

    private int id, balance = 1500, currentPosition=1;
    private String name;
    private boolean isTheirturn= false , isMoneyDeposited=false, isInJail= false;
    private Vector<Lands> ownLands = new Vector<Lands>();

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void move(int diceRoll){
        currentPosition+=diceRoll;
        if (currentPosition>24){
            currentPosition-=24;
        }
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
