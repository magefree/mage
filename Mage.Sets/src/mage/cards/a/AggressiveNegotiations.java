package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 * Aggressive Negotiations implementation
 * Author: @mikejcunn
 */
public final class AggressiveNegotiations extends CardImpl {

    public AggressiveNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player exiles that card.
        Effect effect1 = new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_A_NON_LAND);
        this.getSpellAbility().addEffect(effect1);
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Put a +1/+1 counter on target creature you control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private AggressiveNegotiations(final AggressiveNegotiations card) {
        super(card);
    }

    @Override
    public AggressiveNegotiations copy() {
        return new AggressiveNegotiations(this);
    }
}
