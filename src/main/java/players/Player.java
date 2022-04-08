package players;

import lands.LandsWithRent;
import utilities.*;
import lands.EmptyLands;
import lands.Lands;

import java.io.IOException;
import java.util.*;

public class Player {

    private int id, balance = 1500, currentPosition=1, taxTicket=0, jailTicket=0, diceRoll = -1;
    private String name;
    private boolean isTheirTurn= false , isMoneyDeposited=false, isInJail= false, gotBroke=false;
    //lands that player owns
    private Vector<Lands> ownLands = new Vector<Lands>();
    private List<Actions> actions;
    private Jui.Colors color;
    private boolean actionsDone;
    private int daysInJail = 0;

    public Player(int id, String name, Jui.Colors color) {
        this.id = id;
        this.name = name;
        this.actions = new ArrayList<>();
        this.color = color;
        this.actionsDone = false;
    }

    public Jui.Colors getColor() {
        return color;
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

            if (land.getType().equals(Property.Cinema)){
                int numberOfCinemas = 0;
                for (Lands playerLand: ownLands){
                    if (playerLand.getType().equals(Property.Cinema)) numberOfCinemas++;
                }
                int rent = (int) (25 * Math.pow(2, numberOfCinemas - 1));
                for (Lands playerLand: ownLands){
                    if (playerLand.getType().equals(Property.Cinema)){
                        LandsWithRent cinema = (LandsWithRent) playerLand;
                        cinema.setRent(rent);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void sell (Lands land){
        land.setOwner(null);
        ownLands.remove(land);
        if (land instanceof EmptyLands emptyLand){
            emptyLand.setStructures(new Vector<>());
            emptyLand.setRent(50);
        } else if (land instanceof LandsWithRent landWithRent) {
            landWithRent.setRent(25);
        }
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

    public String getName() {
        return name;
    }

    public List<Actions> getActions(){
        return this.actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void diceRoll() throws IOException {
        int dice = Dice.roll(true);
        if (dice == 6 && this.diceRoll == 6){
            isInJail = true;
            currentPosition = 13;
        }
        this.diceRoll = dice;
    }

    public void setDiceRoll(int i) {
        this.diceRoll = i;
    }

    public boolean isActionsDone() {
        return actionsDone;
    }

    public void setActionsDone(boolean actionsDone) {
        this.actionsDone = actionsDone;
    }

    public void fly(int dest) {
        this.currentPosition = dest;
        this.balance -= 50;
    }

    public Vector<Lands> getOwnLands() {
        return ownLands;
    }

    public int getDaysInJail() {
        return daysInJail;
    }

    public void setDaysInJail(int daysInJail) {
        this.daysInJail = daysInJail;
    }

    public int getId() {
        return id;
    }
}
