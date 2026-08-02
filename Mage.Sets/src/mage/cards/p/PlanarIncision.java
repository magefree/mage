package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author jeffwadsworth
 */
public final class PlanarIncision extends CardImpl {

    public PlanarIncision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target artifact or creature, then return it to the battlefield under its owner’s control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(
            new ExileThenReturnTargetEffect(false, false).withEnterWithCounters(CounterType.P1P1.createInstance())
        );
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private PlanarIncision(final PlanarIncision card) {
        super(card);
    }

    @Override
    public PlanarIncision copy() {
        return new PlanarIncision(this);
    }
}
