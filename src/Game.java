import java.util.Vector;
import java.lang.Math;

public class Game {
    Vector<Player> players;
    Banker banker = new Banker();
    Lands[] lands = new Lands[24];
    Player currentPLayer;
    Dice dice = new Dice();

    public Game(Vector<Player> players){
        this.players=players;
        this.currentPLayer= players.get(0);
    }

    public boolean payRent(){
        LandsWithRent land = (LandsWithRent) (lands[currentPLayer.getCurrentPosition()-1]);
        if (currentPLayer.pay(land.getRent())){
            return true;
        }
        return false;
    }

    public void twiceRent (LandsWithRent land){
        land.setRent(land.getRent()*2);
    }

    public void halfRent (LandsWithRent land){
        land.setRent(land.getRent()/2);
    }

    public void nextTurn(){
        for(int i=0;i<players.size();i++){
            if (players.get(i).equals(currentPLayer)){
                currentPLayer=players.get(i+1);
            }
        }
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
        int a = (int) (Math.random()*(7));
        switch (a){
            case 0: return Cards.PayTenDollarToEveryone;
            case 1: return Cards.DollorGift;
            case 2: return Cards.GoToJail;
            case 3: return Cards.TenPercentPenalty;
            case 4: return Cards.ThreePlaceGoForward;
            case 5: return Cards.TicketToLeaveJail;
            case 6: return Cards.TicketToNotPayTax;
        }
        return null;
    }

    public static void main(String[] args) {
        Vector<Player> vector = new Vector<>();
        vector.add(null);
    }
}
