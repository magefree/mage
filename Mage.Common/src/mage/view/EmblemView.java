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
        expansionSetCode = sourceCard.getExpansionSetCode();
        rules = emblem.getAbilities().getRules(sourceCard.getName());
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
