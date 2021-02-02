
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class InspiringRoar extends CardImpl {

    public InspiringRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Put a +1/+1 counter on each creature you control.
        getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent()));
    }

    private InspiringRoar(final InspiringRoar card) {
        super(card);
    }

    @Override
    public InspiringRoar copy() {
        return new InspiringRoar(this);
    }
}
