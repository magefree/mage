package mage.cards.n;

import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NowForWrathNowForRuin extends CardImpl {

    public NowForWrathNowForRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Put a +1/+1 counter on each creature you control. They gain vigilance until end of turn. The Ring tempts you.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("They gain vigilance until end of turn"));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private NowForWrathNowForRuin(final NowForWrathNowForRuin card) {
        super(card);
    }

    @Override
    public NowForWrathNowForRuin copy() {
        return new NowForWrathNowForRuin(this);
    }
}
