import java.util.Vector;
import java.lang.Math;

public class Game {
    private Player[] players;
    private Banker banker = new Banker();
    private Lands[] lands = new Lands[24];
    private Player currentPLayer;
    private Dice dice = new Dice();

    {
        Player banker = new Player(-1,"banker");
        lands[1]=new EmptyLands(null,Property.Empty,100);
        lands[6]=new EmptyLands(null,Property.Empty,100);
        lands[8]=new EmptyLands(null,Property.Empty,100);
        lands[11]=new EmptyLands(null,Property.Empty,100);
        lands[13]=new EmptyLands(null,Property.Empty,100);
        lands[17]=new EmptyLands(null,Property.Empty,100);
        lands[18]=new EmptyLands(null,Property.Empty,100);
        lands[22]=new EmptyLands(null,Property.Empty,100);
        lands[0]=new Lands(banker,Property.Parking,null);
        lands[2]=new LandsWithRent(banker,Property.Airport,null);
        lands[19]=new LandsWithRent(banker,Property.Airport,null);
        lands[10]=new LandsWithRent(banker,Property.Airport,null);
        lands[3]=new LandsWithRent(null,Property.Cinema,200);
        lands[7]=new LandsWithRent(null,Property.Cinema,200);
        lands[14]=new LandsWithRent(null,Property.Cinema,200);
        lands[21]=new LandsWithRent(null,Property.Cinema,200);
        lands[4]= new Lands(banker,Property.Road,null);
        lands[9]= new Lands(banker,Property.Road,null);
        lands[15]= new Lands(banker,Property.Road,null);
        lands[5]= new Lands(banker,Property.Award,null);
        lands[12]= new Lands(banker,Property.Jail,null);
        lands[16]= new Lands(banker,Property.Tax,null);
        lands[20]= new Lands(banker,Property.Bank,null);
        lands[23]= new Lands(banker,Property.RandomCard,null);
    }

    public Game(Player... players){
        this.players=players;
        this.currentPLayer= players[0];
    }

    public Player[] getPlayers() {
        return players;
    }

    public Banker getBanker() {
        return banker;
    }

    public Lands[] getLands() {
        return lands;
    }

    public Player getCurrentPLayer() {
        return currentPLayer;
    }

    public void setCurrentPLayer(Player currentPLayer) {
        this.currentPLayer = currentPLayer;
    }

    //paying rent for current player
    public boolean payRent(){
        LandsWithRent land = (LandsWithRent) (lands[currentPLayer.getCurrentPosition()-1]);
        if (currentPLayer.pay(land.getRent())){
            return true;
        }
        return false;
    }

    //setting rent twice for same color properties
    public void twiceRent (LandsWithRent land){
        land.setRent(land.getRent()*2);
    }

    //setting rent half for losing same color properties
    public void halfRent (LandsWithRent land){
        land.setRent(land.getRent()/2);
    }

    //next player to play
    public void nextTurn(){
        for(int i=0;i<players.length;i++){
            if (players[i].equals(currentPLayer)){
                currentPLayer=players[i+1];
            }
        }
    }

    //getting cards and doing the thing
    public boolean getCard(Cards card){
        if (card.equals(Cards.PayTenDollarToEveryone)){
            if (currentPLayer.pay(players.length*10)){
                for(int i=0;i<players.length;i++){
                    if (players[i]!=currentPLayer){
                        players[i].getPaid(10);
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

    //giving random card
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

}
