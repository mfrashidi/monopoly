public class LandsWithRent extends Lands{
    private int rent;

    public LandsWithRent(Player owner, Property type,Integer cost) {
        super(owner, type, cost);
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRent() {
        return rent;
    }
}
