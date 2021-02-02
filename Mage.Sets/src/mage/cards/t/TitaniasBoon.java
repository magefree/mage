

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class TitaniasBoon extends CardImpl {

    public TitaniasBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

		
		// Put a +1/+1 counter on each creature you control.
		this.getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED));
    }

    private TitaniasBoon(final TitaniasBoon card) {
        super(card);
    }

    @Override
    public TitaniasBoon copy() {
        return new TitaniasBoon(this);
    }
}
