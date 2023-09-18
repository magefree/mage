package mage.cards.s;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class StrengthOfTheCoalition extends CardImpl {

    public StrengthOfTheCoalition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Target creature you control gets +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));

        // If this spell was kicked, put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
                KickedCondition.ONCE,
                "If this spell was kicked, put a +1/+1 counter on each creature you control."
        ));
    }

    private StrengthOfTheCoalition(final StrengthOfTheCoalition card) {
        super(card);
    }

    @Override
    public StrengthOfTheCoalition copy() {
        return new StrengthOfTheCoalition(this);
    }
}
