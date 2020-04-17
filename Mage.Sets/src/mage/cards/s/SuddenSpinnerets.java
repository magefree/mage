package mage.cards.s;

import mage.abilities.effects.common.UntapTargetEffect;
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
public final class SuddenSpinnerets extends CardImpl {

    public SuddenSpinnerets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +1/+3 until end of turn. Put a reach counter on it. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.REACH.createInstance())
                .setText("Put a reach counter on it"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuddenSpinnerets(final SuddenSpinnerets card) {
        super(card);
    }

    @Override
    public SuddenSpinnerets copy() {
        return new SuddenSpinnerets(this);
    }
}
