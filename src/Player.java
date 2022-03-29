import java.util.*;

public class Player {

    private int id, balance = 1500, currentPosition=1, taxTicket=0, jailTicket=0;
    private String name;
    private boolean isTheirturn= false , isMoneyDeposited=false, isInJail= false;
    private Vector<Lands> ownLands = new Vector<Lands>();

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getTaxTicket() {
        return taxTicket;
    }

    public void setTaxTicket(int taxTicket) {
        this.taxTicket = taxTicket;
    }

    public int getJailTicket() {
        return jailTicket;
    }

    public void setJailTicket(int jailTicket) {
        this.jailTicket = jailTicket;
    }

    public void move(int diceRoll){
        currentPosition+=diceRoll;
        if (currentPosition>24){
            currentPosition-=24;
        }
    }

    public boolean buyProperty(Lands land){
        if (land.getCost()<=this.balance){
            land.setOwner(this);
            balance-=land.getCost();
            ownLands.add(land);
            return true;
        }
        return false;
    }

    public boolean pay(int cost){
        if (this.balance>=cost){
            this.balance-=cost;
            return true;
        }
        return false;
    }

    public boolean equals(Player player) {
        return this.id==player.id;
    }
}
