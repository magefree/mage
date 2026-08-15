package mage.cards.i;

import java.util.UUID;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ImADoctorNotA extends CardImpl {

    public ImADoctorNotA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Put a +1/+1 counter on target creature you control. Untap it. Until end of turn, it gets +1/+1 and becomes a Doctor.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setText("Until end of turn, it gets +1/+1"));
        this.getSpellAbility().addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfTurn, SubType.DOCTOR).setText("and becomes a Doctor"));
    }

    private ImADoctorNotA(final ImADoctorNotA card) {
        super(card);
    }

    @Override
    public ImADoctorNotA copy() {
        return new ImADoctorNotA(this);
    }
}
