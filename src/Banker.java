import java.util.TreeMap;

public class Banker {
    private TreeMap<Integer,Player> deposits = new TreeMap<Integer,Player>();

    public boolean swapWealth (Player player1,Player player2){
        if (player1.isGotBroke() || player2.isGotBroke()){
            return false;
        }
        int temp = player1.getBalance();
        player1.pay(player1.getBalance());
        player1.getPaid(player2.getBalance());
        player2.pay(player2.getBalance());
        player2.getPaid(temp);
        return true;
    }

}
