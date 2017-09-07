package mage.game;

/**
 * Created by Nullifer on 7/23/17.
 */
public class KingdomRole {

    protected String name, description;

    public KingdomRole(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
