public class LandsWithRent extends Lands{
    private int rent;

    public LandsWithRent(Player owner, Property type) {
        super(owner, type);
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRent() {
        return rent;
    }
}
