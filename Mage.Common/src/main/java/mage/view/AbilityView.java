package mage.view;

import java.util.ArrayList;
import java.util.EnumSet;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.SuperType;
import mage.util.SubTypes;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AbilityView extends CardView {

    private static final long serialVersionUID = 1L;

    private final String sourceName;
    private final CardView sourceCard;

    public AbilityView(Ability ability, String sourceName, CardView sourceCard) {
        this.id = ability.getId();
        this.name = "Ability";
        this.sourceName = sourceName;
        this.sourceCard = sourceCard;
        this.rules = new ArrayList<>();
        rules.add(ability.getRule());
        this.power = "";
        this.toughness = "";
        this.loyalty = "";
        this.cardTypes = new ArrayList<>();
        this.subTypes = new SubTypes();
        this.superTypes = EnumSet.noneOf(SuperType.class);
        this.color = new ObjectColor();
        this.manaCostLeftStr = String.join("", ability.getManaCostSymbols());
        this.manaCostRightStr = "";
    }

    public CardView getSourceCard() {
        return this.sourceCard;
    }

    public void setName(String name) {
        this.name = name;
    }

}
