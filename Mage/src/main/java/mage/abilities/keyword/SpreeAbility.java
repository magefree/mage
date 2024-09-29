package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class SpreeAbility extends StaticAbility {

    public SpreeAbility(Card card) {
        super(Zone.ALL, null);
        this.setRuleVisible(false);
        card.getSpellAbility().getModes().setChooseText("Spree <i>(Choose one or more additional costs.)</i>");
        card.getSpellAbility().getModes().setMinModes(1);
        card.getSpellAbility().getModes().setMaxModes(Integer.MAX_VALUE);
    }

    private SpreeAbility(final SpreeAbility ability) {
        super(ability);
    }

    @Override
    public SpreeAbility copy() {
        return new SpreeAbility(this);
    }
}
