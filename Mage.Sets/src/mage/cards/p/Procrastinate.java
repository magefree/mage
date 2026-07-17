package mage.cards.p;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Procrastinate extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public Procrastinate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // Tap target creature. Put twice X stun counters on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.STUN.createInstance(), xValue
        ).setText("Put twice X stun counters on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Procrastinate(final Procrastinate card) {
        super(card);
    }

    @Override
    public Procrastinate copy() {
        return new Procrastinate(this);
    }
}
