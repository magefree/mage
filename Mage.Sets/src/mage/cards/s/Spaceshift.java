package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class Spaceshift extends CardImpl {

    public Spaceshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target artifact or creature, then return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, true).withAfterEffect(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("with a +1/+1 counter on it")
        ));
    }

    private Spaceshift(final Spaceshift card) {
        super(card);
    }

    @Override
    public Spaceshift copy() {
        return new Spaceshift(this);
    }
}
