package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenbeltGuardian extends CardImpl {

    public GreenbeltGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}: Target creature gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(TrampleAbility.getInstance()), new ManaCostsImpl<>("{G}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Exhaust -- {3}{G}: Put three +1/+1 counters on this creature.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), new ManaCostsImpl<>("{3}{G}")
        ));
    }

    private GreenbeltGuardian(final GreenbeltGuardian card) {
        super(card);
    }

    @Override
    public GreenbeltGuardian copy() {
        return new GreenbeltGuardian(this);
    }
}
