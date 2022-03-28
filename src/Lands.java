public class Lands {
    private Player owner;
    private Property type;
    public Lands(Player owner, Property type) {
        this.owner = owner;
        this.type = type;
    }

    public Player getOwner() {
        return owner;
    }

    public Property getType() {
        return type;
    }
}
