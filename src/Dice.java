import java.lang.Math;

public class Dice {
    private int diceRoll;

    public int getDiceRoll() {
        return diceRoll;
    }

    public void roll(){
        int a = (int) (Math.random()*(6)+1);
    }
}
