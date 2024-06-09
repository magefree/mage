package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class RedManaBattery extends CardImpl {

    public RedManaBattery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Put a charge counter on Red Mana Battery.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}, Remove any number of charge counters from Red Mana Battery: Add {R}, 
        // then add an additional {R} for each charge counter removed this way.
        ability = new DynamicManaAbility(
                Mana.RedMana(1),
                new IntPlusDynamicValue(1, RemovedCountersForCostValue.instance),
                new TapSourceCost(),
                "Add {R}, then add {R} for each charge counter removed this way",
                true, new IntPlusDynamicValue(1, new CountersSourceCount(CounterType.CHARGE)));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE,
                "Remove any number of charge counters from {this}"));
        this.addAbility(ability);
    }

    private RedManaBattery(final RedManaBattery card) {
        super(card);
    }

    @Override
    public RedManaBattery copy() {
        return new RedManaBattery(this);
    }
}
