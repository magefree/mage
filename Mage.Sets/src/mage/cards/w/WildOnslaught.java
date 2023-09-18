
package mage.cards.w;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class WildOnslaught extends CardImpl {

    public WildOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Kicker {4} (You may pay an additional {4} as you cast this spell.)
        this.addAbility(new KickerAbility("{4}"));

        // Put a +1/+1 counter on each creature you control. If this spell was kicked, put two +1/+1 counters on each creature you control instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(2), StaticFilters.FILTER_CONTROLLED_CREATURE),
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
                KickedCondition.ONCE,
                "Put a +1/+1 counter on each creature you control. If this spell was kicked, put two +1/+1 counters on each creature you control instead."));

    }

    private WildOnslaught(final WildOnslaught card) {
        super(card);
    }

    @Override
    public WildOnslaught copy() {
        return new WildOnslaught(this);
    }
}
