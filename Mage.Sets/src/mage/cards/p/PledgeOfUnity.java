package mage.cards.p;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class PledgeOfUnity extends CardImpl {

    public PledgeOfUnity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{W}");

        // Put a +1/+1 counter on each creature you control. You gain 1 life for each creature you control.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)
        ).setText("You gain 1 life for each creature you control."));
    }

    private PledgeOfUnity(final PledgeOfUnity card) {
        super(card);
    }

    @Override
    public PledgeOfUnity copy() {
        return new PledgeOfUnity(this);
    }
}
