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
public final class FullyGrown extends CardImpl {

    public FullyGrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature gets +3/+3 until end of turn. Put a trample counter on it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.TRAMPLE.createInstance())
                .setText("Put a trample counter on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FullyGrown(final FullyGrown card) {
        super(card);
    }

    @Override
    public FullyGrown copy() {
        return new FullyGrown(this);
    }
}
