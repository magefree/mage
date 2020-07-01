package mage.cards.b;

import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasrisSolidarity extends CardImpl {

    public BasrisSolidarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
    }

    private BasrisSolidarity(final BasrisSolidarity card) {
        super(card);
    }

    @Override
    public BasrisSolidarity copy() {
        return new BasrisSolidarity(this);
    }
}
