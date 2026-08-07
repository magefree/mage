package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EmergencyMedicalHologram extends CardImpl {

    public EmergencyMedicalHologram(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // When this creature enters, put a +1/+1 counter on target creature. You gain 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);
    }

    private EmergencyMedicalHologram(final EmergencyMedicalHologram card) {
        super(card);
    }

    @Override
    public EmergencyMedicalHologram copy() {
        return new EmergencyMedicalHologram(this);
    }
}
