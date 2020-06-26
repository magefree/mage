package mage.cards.o;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class Order66 extends CardImpl {

    public Order66(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{B}{B}");

        // Put a bounty counter on each creature you don't control, then destroy all creatures you don't control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.BOUNTY.createInstance(), StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private Order66(final Order66 card) {
        super(card);
    }

    @Override
    public Order66 copy() {
        return new Order66(this);
    }
}
