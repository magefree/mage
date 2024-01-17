package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.cards.Card;

/**
 * @author TheElk801
 * TODO: Implement this
 */
public class DisguiseAbility extends SpellAbility {

    public DisguiseAbility(Card card, Cost disguiseCost) {
        super(new GenericManaCost(3), card.getName());
    }

    private DisguiseAbility(final DisguiseAbility ability) {
        super(ability);
    }

    @Override
    public DisguiseAbility copy() {
        return new DisguiseAbility(this);
    }

    @Override
    public String getRule() {
        return "Disguise";
    }
}
