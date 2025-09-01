package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class TieredAbility extends StaticAbility {

    public TieredAbility(Card card) {
        super(Zone.ALL, null);
        this.setRuleVisible(false);
        card.getSpellAbility().getModes().setChooseText("Tiered <i>(Choose one additional cost.)</i>");
    }

    private TieredAbility(final TieredAbility ability) {
        super(ability);
    }

    @Override
    public TieredAbility copy() {
        return new TieredAbility(this);
    }
}
