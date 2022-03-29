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
        if (card.equals(Cards.PayTenDollarToEveryone)){
            if (currentPLayer.pay(players.size()*10)){
                for(int i=0;i<players.size();i++){
                    if (players.get(i)!=currentPLayer){
                        players.get(i).getPaid(10);
                    }
                }
            }
            else{
                return false;
            }
        }
        if (card.equals(Cards.DollorGift)){
            currentPLayer.getPaid(200);
        }
        if (card.equals(Cards.GoToJail)){
            currentPLayer.setCurrentPosition(13);
            currentPLayer.setInJail(true);
        }
        if (card.equals(Cards.TenPercentPenalty)){
            currentPLayer.pay(currentPLayer.getBalance()/10);
        }
        if (card.equals(Cards.ThreePlaceGoForward)){
            currentPLayer.move(3);
        }
        if (card.equals(Cards.TicketToLeaveJail)){
            currentPLayer.setJailTicket(currentPLayer.getJailTicket()+1);
        }
        if (card.equals(Cards.TicketToNotPayTax)){
            currentPLayer.setTaxTicket(currentPLayer.getTaxTicket()+1);
        }
        return true;
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
