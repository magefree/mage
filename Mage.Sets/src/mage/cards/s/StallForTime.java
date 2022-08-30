package mage.cards.s;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class StallForTime extends CardImpl {

    public StallForTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Tap up to two target creatures.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new TapTargetEffect());

        // If this spell was kicked, put a stun counter on each of those creatures.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.STUN.createInstance()),
                KickedCondition.ONCE,
                "If this spell was kicked, put a stun counter on each of those creatures."
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private StallForTime(final StallForTime card) {
        super(card);
    }

    @Override
    public StallForTime copy() {
        return new StallForTime(this);
    }
}
