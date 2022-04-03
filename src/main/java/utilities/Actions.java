package utilities;

public enum Actions {
    RollDice,
    Buy,
    Build,
    Sell,
    Fly,
    Free,
    Invest,
    Next;

    @Override
    public String toString(){
        if (name().equals("RollDice")) return "Roll Dice";
        else return name();
    }
}
