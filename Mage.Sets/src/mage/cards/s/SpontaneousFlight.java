package mage.cards.s;

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
public final class SpontaneousFlight extends CardImpl {

    public SpontaneousFlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Target creature gets +2/+2 until end of turn. Put a flying counter on it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FLYING.createInstance())
                .setText("Put a flying counter on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SpontaneousFlight(final SpontaneousFlight card) {
        super(card);
    }

    @Override
    public SpontaneousFlight copy() {
        return new SpontaneousFlight(this);
    }
}
