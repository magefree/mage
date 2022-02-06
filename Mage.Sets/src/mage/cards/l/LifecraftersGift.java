
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LifecraftersGift extends CardImpl {

    public LifecraftersGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Put a +1/+1 counter on target creature,
        getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // then put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1
        ).concatBy(", then"));
    }

    private LifecraftersGift(final LifecraftersGift card) {
        super(card);
    }

    @Override
    public LifecraftersGift copy() {
        return new LifecraftersGift(this);
    }
}
