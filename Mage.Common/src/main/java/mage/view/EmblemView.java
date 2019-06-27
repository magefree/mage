package mage.view;

import mage.cards.Card;
import mage.game.command.Emblem;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author noxx
 */
public class EmblemView implements CommandObjectView, Serializable {

    protected UUID id;
    protected String name;
    protected String expansionSetCode;
    protected List<String> rules;
    protected boolean isPlayable = false;

    public EmblemView(Emblem emblem, Card sourceCard) {
        this.id = emblem.getId();
        this.name = "Emblem " + sourceCard.getName();
        if (emblem.getExpansionSetCodeForImage() == null) {
            this.expansionSetCode = sourceCard.getExpansionSetCode();
        } else {
            this.expansionSetCode = emblem.getExpansionSetCodeForImage();
        }
        this.rules = emblem.getAbilities().getRules(sourceCard.getName());
    }

    public EmblemView(Emblem emblem) {
        this.id = emblem.getId();
        this.name = emblem.getName();
        this.expansionSetCode = emblem.getExpansionSetCodeForImage();
        this.rules = emblem.getAbilities().getRules(emblem.getName());
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
