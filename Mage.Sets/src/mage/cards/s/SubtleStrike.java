package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SubtleStrike extends CardImpl {

    public SubtleStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // • Target creature gets -1/-1 until end of turn.
        BoostTargetEffect minusOneMinusOne = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        minusOneMinusOne.setText("Target creature gets -1/-1 until end of turn");
        this.getSpellAbility().addEffect(minusOneMinusOne);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets -1/-1 until end of turn"));
        // • Put a +1/+1 counter on target creature.
        AddCountersTargetEffect plusOnePlusOneCounter = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        plusOnePlusOneCounter.setText("Put a +1/+1 counter on target creature");
        Mode mode1 = new Mode(plusOnePlusOneCounter);
        mode1.addTarget(new TargetCreaturePermanent().withChooseHint("gets +1/+1 counter"));
        this.getSpellAbility().addMode(mode1);
    }

    private SubtleStrike(final SubtleStrike card) {
        super(card);
    }

    @Override
    public SubtleStrike copy() {
        return new SubtleStrike(this);
    }
}
