import java.util.*;

public class Player {

    private int id, balance = 1500, currentPosition=1, taxTicket=0, jailTicket=0;
    private String name;
    private boolean isTheirturn= false , isMoneyDeposited=false, isInJail= false;
    private Vector<Lands> ownLands = new Vector<Lands>();

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean build(EmptyLands land){
        if (!(land instanceof EmptyLands) || land.structures.get(0).equals(Structures.Hotels)){
            return false;
        }
        for(int i=0; i<ownLands.size();i++){
            EmptyLands temp = (EmptyLands) ownLands.get(i);
            if(temp.structures.size()!=land.structures.size()){
                return false;
            }
        }
        if(land.structures.size()<4 && !(land.structures.get(0).equals(Structures.Hotels))){
            if(!(this.pay(150))){
                return false;
            }
            land.setRent(land.getRent()+100);
            land.structures.add(Structures.Buildings);
        }
        if(land.structures.size()==4){
            if(!(this.pay(100))){
                return false;
            }
            land.setRent(land.getRent()+150);
            land.structures.clear();
            land.structures.add(Structures.Hotels);
        }
        return true;
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setTheirturn(boolean theirturn) {
        isTheirturn = theirturn;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setMoneyDeposited(boolean moneyDeposited) {
        isMoneyDeposited = moneyDeposited;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
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

    public void getPaid(int amount){
        balance+=amount;
    }

    public int getBalance() {
        return balance;
    }

    public boolean equals(Player player) {
        return this.id==player.id;
    }
}
