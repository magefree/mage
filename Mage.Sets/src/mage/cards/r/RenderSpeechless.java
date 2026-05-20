package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class RenderSpeechless extends CardImpl {

    public RenderSpeechless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
            StaticFilters.FILTER_CARD_NON_LAND
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Put two +1/+1 counters on up to one target creature.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(2));
        effect.setText("<br>Put two +1/+1 counters on up to one target creature");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private RenderSpeechless(final RenderSpeechless card) {
        super(card);
    }

    @Override
    public RenderSpeechless copy() {
        return new RenderSpeechless(this);
    }
}
