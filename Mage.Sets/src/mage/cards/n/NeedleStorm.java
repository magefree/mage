
package mage.cards.n;

import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Loki
 */
public final class NeedleStorm extends CardImpl {

    public NeedleStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        this.getSpellAbility().addEffect(new DamageAllEffect(4, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private NeedleStorm(final NeedleStorm card) {
        super(card);
    }

    @Override
    public NeedleStorm copy() {
        return new NeedleStorm(this);
    }
}
