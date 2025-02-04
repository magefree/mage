package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.mana.builder.common.ActivatedAbilityManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jackd149
 */
public final class Boommobile extends CardImpl {

    public Boommobile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When this Vehicle enters, add four mana of any one color. Spend this mana only to activate abilities.
        ManaEffect entersEffect = new AddConditionalManaOfAnyColorEffect(4, new ActivatedAbilityManaBuilder());
        entersEffect.setText("add four mana of any one color. Spend this mana only to activate abilities.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(entersEffect));

        // Exhaust -- {X}{2}{R}: This vehicle deals X damage to any target. Put a +1/+1 counter on this Vehicle.
        Ability exhaustAbility = new ExhaustAbility(new DamageTargetEffect(GetXValue.instance), new ManaCostsImpl<>("{X}{2}{R}"));
        exhaustAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        exhaustAbility.addTarget(new TargetAnyTarget());
        this.addAbility(exhaustAbility);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private Boommobile(final Boommobile card) {
        super(card);
    }

    @Override
    public Boommobile copy() {
        return new Boommobile(this);
    }
}
