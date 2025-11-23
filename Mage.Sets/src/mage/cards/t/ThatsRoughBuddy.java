package mage.cards.t;

import mage.abilities.condition.common.CreatureLeftThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThatsRoughBuddy extends CardImpl {

    public ThatsRoughBuddy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.subtype.add(SubType.LESSON);

        // Put a +1/+1 counter on target creature. Put two +1/+1 counters on that creature instead if a creature left the battlefield under your control this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                CreatureLeftThisTurnCondition.instance, "put a +1/+1 counter on target creature. Put two +1/+1 " +
                "counters on that creature instead if a creature left the battlefield under your control this turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(CreatureLeftThisTurnCondition.getHint());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ThatsRoughBuddy(final ThatsRoughBuddy card) {
        super(card);
    }

    @Override
    public ThatsRoughBuddy copy() {
        return new ThatsRoughBuddy(this);
    }
}
