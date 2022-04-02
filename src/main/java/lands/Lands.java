package lands;

import players.Player;
import utilities.Jui;
import utilities.Property;

public class Lands {
    private Player owner;
    private Property type;
    private Integer cost;
    private Jui.Colors color;
    private String name;
    public Lands(Player owner, Property type, Integer cost, Jui.Colors color, String name) {
        this.owner = owner;
        this.type = type;
        this.cost = cost;
        this.color = color;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
