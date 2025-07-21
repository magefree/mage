
package mage.cards.p;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PierceTheSky extends CardImpl {

    public PierceTheSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Pierce the Sky deals 7 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private PierceTheSky(final PierceTheSky card) {
        super(card);
    }

    @Override
    public PierceTheSky copy() {
        return new PierceTheSky(this);
    }
}
