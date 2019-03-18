

package mage.view;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.util.SubTypeList;

import java.util.ArrayList;
import java.util.EnumSet;

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
        this.cardTypes = EnumSet.noneOf(CardType.class);
        this.subTypes = new SubTypeList();
        this.superTypes =EnumSet.noneOf(SuperType.class);
        this.color = new ObjectColor();
        this.manaCost = ability.getManaCosts().getSymbols();
    }

    public CardView getSourceCard() {
        return this.sourceCard;
    }

    public void setName(String name) {
        this.name = name;
    }


}
