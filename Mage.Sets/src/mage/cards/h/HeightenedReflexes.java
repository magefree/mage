package mage.cards.h;

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
public final class HeightenedReflexes extends CardImpl {

    public HeightenedReflexes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +1/+0 until end of turn. Put a first strike counter on it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FIRST_STRIKE.createInstance())
                .setText("Put a first strike counter on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HeightenedReflexes(final HeightenedReflexes card) {
        super(card);
    }

    @Override
    public HeightenedReflexes copy() {
        return new HeightenedReflexes(this);
    }
}
