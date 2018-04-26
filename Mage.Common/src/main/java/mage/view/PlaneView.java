package mage.view;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.cards.Card;
import mage.game.command.Plane;

/**
 * @author spjspj
 */
public class PlaneView implements CommandObjectView, Serializable {

    protected UUID id;
    protected String name;
    protected String expansionSetCode;
    protected List<String> rules;

    public PlaneView(Plane plane, Card sourceCard) {
        id = plane.getId();
        name = "Plane " + sourceCard.getName();
        if (plane.getExpansionSetCodeForImage() == null) {
            expansionSetCode = sourceCard.getExpansionSetCode();
        } else {
            expansionSetCode = plane.getExpansionSetCodeForImage();
        }

        rules = plane.getAbilities().getRules(sourceCard.getName());
    }

    public PlaneView(Plane plane) {
        id = plane.getId();
        name = plane.getName();
        expansionSetCode = plane.getExpansionSetCodeForImage();
        rules = plane.getAbilities().getRules(plane.getName());
    }

    @Override
    public String getExpansionSetCode() {
        return expansionSetCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public List<String> getRules() {
        return rules;
    }
}
