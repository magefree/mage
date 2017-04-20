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

    public EmblemView(Emblem emblem, Card sourceCard) {
        id = emblem.getId();
        name = "Emblem " + sourceCard.getName();
        if (emblem.getExpansionSetCodeForImage() == null) {
            expansionSetCode = sourceCard.getExpansionSetCode();
        } else {
            expansionSetCode = emblem.getExpansionSetCodeForImage();
        }

        rules = emblem.getAbilities().getRules(sourceCard.getName());
    }

    public EmblemView(Emblem emblem) {
        id = emblem.getId();
        name = emblem.getName();
        expansionSetCode = emblem.getExpansionSetCodeForImage();
        rules = emblem.getAbilities().getRules(emblem.getName());
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final EmblemView that = (EmblemView) o;

        if (!getId().equals(that.getId())) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getExpansionSetCode() != null ? !getExpansionSetCode().equals(that
            .getExpansionSetCode()) : that.getExpansionSetCode() != null)
            return false;
        return getRules() != null ? getRules().equals(that.getRules()) : that.getRules() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getExpansionSetCode() != null ? getExpansionSetCode().hashCode()
            : 0);
        result = 31 * result + (getRules() != null ? getRules().hashCode() : 0);
        return result;
    }
}
