package lands;

import players.Player;
import utilities.Jui;
import utilities.Property;

public class Lands {
    private Player owner;
    private Property type;
    private Integer cost;
    private Jui.Colors color;
    public Lands(Player owner, Property type, Integer cost, Jui.Colors color) {
        this.owner = owner;
        this.type = type;
        this.cost = cost;
        this.color = color;
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

    public Jui.Colors getColor() {
        return color;
    }
}
