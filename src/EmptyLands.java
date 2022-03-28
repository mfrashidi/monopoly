import java.util.Vector;

public class EmptyLands extends LandsWithRent {
    private Vector<Structures> structures = new Vector<>();
    public EmptyLands(Player owner, Property type) {
        super(owner, type);
    }
}
