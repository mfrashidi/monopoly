package players;

import lands.EmptyLands;
import lands.Lands;
import utilities.Actions;
import utilities.Structures;

import java.util.*;

public class Player {

    private int id, balance = 1500, currentPosition=1, taxTicket=0, jailTicket=0;
    private String name;
    private boolean isTheirTurn= false , isMoneyDeposited=false, isInJail= false, gotBroke=false;
    //lands that player owns
    private Vector<Lands> ownLands = new Vector<Lands>();
    private Actions[] actions;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.actions = new Actions[0];
    }

    public boolean isGotBroke() {
        return gotBroke;
    }

    public void setGotBroke(boolean gotBroke) {
        this.gotBroke = gotBroke;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean isTheirTurn(){
        return isTheirTurn;
    }

    public void setTheirTurn(boolean theirTurn) {
        isTheirTurn = theirTurn;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isMoneyDeposited(){
        return isMoneyDeposited;
    }

    public void setMoneyDeposited(boolean moneyDeposited) {
        isMoneyDeposited = moneyDeposited;
    }

    public boolean isInJail() {
        return isInJail;
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

    public int getBalance() {
        return balance;
    }

    //build structures on emptyLands
    public boolean build(EmptyLands land){
        if (land.structures.get(0).equals(Structures.Hotels)){
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

    //move forward
    public void move(int diceRoll){
        currentPosition+=diceRoll;
        if (currentPosition>24){
            currentPosition-=24;
        }
    }

    //buying Property for player
    public boolean buyProperty(Lands land){
        if (land.getCost()<=this.balance){
            land.setOwner(this);
            balance-=land.getCost();
            ownLands.add(land);
            return true;
        }
        return false;
    }

    //paying something
    public boolean pay(int cost){
        if (this.balance>=cost){
            this.balance-=cost;
            return true;
        }
        return false;
    }

    //getting money for something
    public void getPaid(int amount){
        balance+=amount;
    }

    public boolean equals(Player player) {
        return this.id==player.id;
    }

    public Actions[] getActions(){
        return this.actions;
    }

    public void updateActions(){

    }
}
