package mage.cards.s;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScaleTheHeights extends CardImpl {

    public ScaleTheHeights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Put a +1/+1 counter on up to one target creature. You gain 2 life. You may play an additional land this turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).setText("You gain 2 life."));
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ScaleTheHeights(final ScaleTheHeights card) {
        super(card);
    }

    @Override
    public ScaleTheHeights copy() {
        return new ScaleTheHeights(this);
    }
}
