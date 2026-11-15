package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ChargeTheSanctum extends CardImpl {

    public ChargeTheSanctum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R/W}");

        // Choose one --
        // * Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));

        // * Target creature gets +2/+0 and gains first strike until end of turn. Put a +1/+1 counter on it.
        Mode mode = new Mode(new BoostTargetEffect(2, 0, Duration.EndOfTurn).setText("target creature gets +2/+0"));
        mode.addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance()).setText("and gains first strike until end of turn"));
        mode.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("Put a +1/+1 counter on it"));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private ChargeTheSanctum(final ChargeTheSanctum card) {
        super(card);
    }

    @Override
    public ChargeTheSanctum copy() {
        return new ChargeTheSanctum(this);
    }
}
