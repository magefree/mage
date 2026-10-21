package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheDoctorEMH extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
        new FilterControlledCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TheDoctorEMH(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G/W}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When The Doctor enters, put a +1/+1 counter on target creature. Then put a +1/+1 counter on each creature you control with a counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
            .setText("Then put a +1/+1 counter on each creature you control with a counter on it"));
        this.addAbility(ability);
    }

    private TheDoctorEMH(final TheDoctorEMH card) {
        super(card);
    }

    @Override
    public TheDoctorEMH copy() {
        return new TheDoctorEMH(this);
    }
}
