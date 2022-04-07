package lands;

import players.Player;
import utilities.Jui;
import utilities.Property;
import utilities.Structures;

import java.util.Vector;

public class EmptyLands extends LandsWithRent {
    public Vector<Structures> structures = new Vector<>();
    public EmptyLands(Player owner, Property type, Integer cost, Jui.Colors color, String name, String icon) {
        super(owner, type, cost, color, name, icon);
        super.setRent(50);
    }

    public Vector<Structures> getStructures() {
        return structures;
    }
}
