package players;

import java.util.TreeMap;

public class Banker {
    //a list for deposits
    private TreeMap<Integer,Integer> deposits = new TreeMap<>();

    //swapping wealth for some reason :/
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

    //checking for deposit and pay it
    public boolean checkDeposit(Player player){
        if (deposits.containsKey(player.getId())){
            player.getPaid(deposits.get(player.getId()) * 2);
            player.setMoneyDeposited(false);
            deposits.remove(player.getId());
            return true;
        }
        return false;
    }

    //adding deposit for someone
    public void addDeposit(Player player){
        player.setMoneyDeposited(true);
        deposits.put(player.getId(), player.getBalance()/2);
        player.pay(player.getBalance()/2);
    }
}
