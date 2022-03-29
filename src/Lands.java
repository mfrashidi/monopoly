public class Lands {
    private Player owner;
    private Property type;
    private Integer cost;
    public Lands(Player owner, Property type, Integer cost) {
        this.owner = owner;
        this.type = type;
        this.cost = cost;
    }

    public Player getOwner() {
        return owner;
    }

    public Property getType() {
        return type;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer getCost() {
        return cost;
    }
}
