package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * Aggressive Negotiations implementation
 * Author: @mikejcunn
 */
public final class AggressiveNegotiations extends CardImpl {

    public AggressiveNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player exiles that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ExileCardYouChooseTargetOpponentEffect(StaticFilters.FILTER_CARD_NON_LAND));

        // Put a +1/+1 counter on target creature you control.
        TargetCreaturePermanent targetCreature = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(targetCreature);
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(1), Outcome.BoostCreature));
    }

    private AggressiveNegotiations(final AggressiveNegotiations card) {
        super(card);
    }

    @Override
    public AggressiveNegotiations copy() {
        return new AggressiveNegotiations(this);
    }
}
