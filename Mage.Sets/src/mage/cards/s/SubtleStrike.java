
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SubtleStrike extends CardImpl {

    public SubtleStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // • Target creature gets -1/-1 until end of turn.
        BoostTargetEffect minusOneMinusOne = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        minusOneMinusOne.setText("Target creature gets -1/-1 until end of turn");
        this.getSpellAbility().addEffect(minusOneMinusOne);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // • Put a +1/+1 counter on target creature.
        Mode mode1 = new Mode();
        AddCountersTargetEffect plusOnePlusOneCounter = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        plusOnePlusOneCounter.setText("Put a +1/+1 counter on target creature");
        mode1.getEffects().add(plusOnePlusOneCounter);
        mode1.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode1);

    }

    public SubtleStrike(final SubtleStrike card) {
        super(card);
    }

    @Override
    public SubtleStrike copy() {
        return new SubtleStrike(this);
    }
}
