import java.util.Vector;

public class EmptyLands extends LandsWithRent {
    public Vector<Structures> structures = new Vector<>();
    public EmptyLands(Player owner, Property type, Integer cost) {
        super(owner, type, cost);
    }
}
