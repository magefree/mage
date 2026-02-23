package mage.cards.d;

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
public final class DarknessDescends extends CardImpl {

    public DarknessDescends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Put two -1/-1 counters on each creature.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.M1M1.createInstance(2), StaticFilters.FILTER_PERMANENT_CREATURE
        ));
    }

    private DarknessDescends(final DarknessDescends card) {
        super(card);
    }

    @Override
    public DarknessDescends copy() {
        return new DarknessDescends(this);
    }
}
