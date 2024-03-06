package mage.cards.i;

import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvigoratingSurge extends CardImpl {

    public InvigoratingSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on that creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new DoubleCountersTargetEffect(CounterType.P1P1)
                .setText(", then double the number of +1/+1 counters on that creature")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private InvigoratingSurge(final InvigoratingSurge card) {
        super(card);
    }

    @Override
    public InvigoratingSurge copy() {
        return new InvigoratingSurge(this);
    }
}
