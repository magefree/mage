
package mage.cards.s;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Squall extends CardImpl {

    public Squall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Squall deals 2 damage to each creature with flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private Squall(final Squall card) {
        super(card);
    }

    @Override
    public Squall copy() {
        return new Squall(this);
    }
}
