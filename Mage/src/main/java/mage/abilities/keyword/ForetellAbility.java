package mage.abilities.keyword;

import mage.abilities.SpecialAction;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class ForetellAbility extends SpecialAction {

    // TODO: Implement this
    public ForetellAbility(Card card, String manaCost) {
        super(Zone.HAND);
    }

    private ForetellAbility(ForetellAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "";
    }

    @Override
    public ForetellAbility copy() {
        return new ForetellAbility(this);
    }
}
