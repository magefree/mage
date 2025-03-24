package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * TODO: Implement this
 *
 * @author TheElk801
 */
public class HarmonizeAbility extends SpellAbility {

    public HarmonizeAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName(), Zone.GRAVEYARD);
    }

    private HarmonizeAbility(final HarmonizeAbility ability) {
        super(ability);
    }

    @Override
    public HarmonizeAbility copy() {
        return new HarmonizeAbility(this);
    }
}
