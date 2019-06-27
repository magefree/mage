package mage.view;

import mage.cards.Card;
import mage.game.command.Plane;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public class PlaneView implements CommandObjectView, Serializable {

    protected UUID id;
    protected String name;
    protected String expansionSetCode;
    protected List<String> rules;

    protected boolean isPlayable = false;

    public PlaneView(Plane plane, Card sourceCard) {
        this.id = plane.getId();
        this.name = "Plane " + sourceCard.getName();
        if (plane.getExpansionSetCodeForImage() == null) {
            this.expansionSetCode = sourceCard.getExpansionSetCode();
        } else {
            this.expansionSetCode = plane.getExpansionSetCodeForImage();
        }
        this.rules = plane.getAbilities().getRules(sourceCard.getName());
    }

    public PlaneView(Plane plane) {
        this.id = plane.getId();
        this.name = plane.getName();
        this.expansionSetCode = plane.getExpansionSetCodeForImage();
        this.rules = plane.getAbilities().getRules(plane.getName());
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

    @Override
    public boolean isPlayable() {
        return isPlayable;
    }

    @Override
    public void setPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    @Override
    public boolean isChoosable() {
        // unsupported
        return false;
    }

    @Override
    public void setChoosable(boolean isChoosable) {
        // unsupported
    }

    @Override
    public boolean isSelected() {
        // unsupported
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        // unsupported
    }
}
