package lands;

import players.Player;
import utilities.Jui;
import utilities.Property;

public class LandsWithRent extends Lands{
    private int rent;

    public LandsWithRent(Player owner, Property type,Integer cost, Jui.Colors color, String name) {
        super(owner, type, cost, color, name);
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRent() {
        return rent;
    }
}
