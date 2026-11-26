package mage.cards.h;

import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Humiliate extends CardImpl {

    public Humiliate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card. Put a +1/+1 counter on a creature you control.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND
        ));
        this.getSpellAbility().addEffect(new OneShotNonTargetEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("Put a +1/+1 counter on a creature you control"),
                new TargetControlledCreaturePermanent().withChooseHint("+1/+1 counter")
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Humiliate(final Humiliate card) {
        super(card);
    }

    @Override
    public Humiliate copy() {
        return new Humiliate(this);
    }
}
