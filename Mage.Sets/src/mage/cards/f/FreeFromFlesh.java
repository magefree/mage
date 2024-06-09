package mage.cards.f;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
public final class FreeFromFlesh extends CardImpl {

    public FreeFromFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +2/+2 until end of turn. Put two oil counters on it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.OIL.createInstance(2)
        ).setText("Put two oil counters on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FreeFromFlesh(final FreeFromFlesh card) {
        super(card);
    }

    @Override
    public FreeFromFlesh copy() {
        return new FreeFromFlesh(this);
    }
}
