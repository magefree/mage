package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class MoltenHydra extends CardImpl {

    public MoltenHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}{R}: Put a +1/+1 counter on Molten Hydra.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new ManaCostsImpl<>("{1}{R}{R}")
        ));

        // {tap}, Remove all +1/+1 counters from Molten Hydra: Molten Hydra deals damage to any target equal to the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(MoltenHydraDynamicValue.instance), new TapSourceCost()
        );
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.P1P1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    public MoltenHydra(final MoltenHydra card) {
        super(card);
    }

    @Override
    public MoltenHydra copy() {
        return new MoltenHydra(this);
    }
}

enum MoltenHydraDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        int count = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                count += ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return count;
    }

    @Override
    public MoltenHydraDynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "the number of +1/+1 counters removed this way";
    }
}
