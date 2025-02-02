package mage.cards.b;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddConditionalManaOfAnyColorEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddConditionalManaOfAnyColorEffect(4, new BoommobileManaBuilder())));
        
        // Exhaust -- {X}{2}{R}: This vehicle deals X damage to any target. Put a +1/+1 counter on this Vehicle.
        ExhaustAbility exhaustAbility = new ExhaustAbility(new DamageTargetEffect(GetXValue.instance), new ManaCostsImpl<>("{X}{2}{R}"));
        exhaustAbility.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addAbility(exhaustAbility);
    }

    private Boommobile(final Boommobile card) {
        super(card);
    }

    @Override
    public Boommobile copy() {
        return new Boommobile(this);
    }
}

class BoommobileManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new BoommobileConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities";
    }
}

class BoommobileConditionalMana extends ConditionalMana {

    BoommobileConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities";
        addCondition(new BoommobileManaCondition());
    }
}

class BoommobileManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        return source != null && source.isActivated();
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
