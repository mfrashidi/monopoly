import java.util.Vector;
import java.lang.Math;

public class Game {
    Vector<Player> players;
    Banker banker = new Banker();
    Lands[] lands = new Lands[24];
    Player currentPLayer;
    Dice dice = new Dice();

    public Game(Vector<Player> players){
        //TODO
    }

    public void twiceRent (LandsWithRent land){
        land.setRent(land.getRent()*2);
    }

    public void halfRent (LandsWithRent land){
        land.setRent(land.getRent()/2);
    }

    public void nextTurn(){
        //TODO
    }

    public void setCurrentPLayer(Player currentPLayer) {
        this.currentPLayer = currentPLayer;
    }

    public Player getCurrentPLayer() {
        return currentPLayer;
    }

    public boolean getCard(Cards card){
        //TODO
        return false;
    }

    public Cards giveCard(){
        //TODO
        return null;
    }
}
