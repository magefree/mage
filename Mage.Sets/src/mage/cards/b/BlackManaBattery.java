
package mage.cards.b;

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
public final class BlackManaBattery extends CardImpl {

    public BlackManaBattery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}: Put a charge counter on Black Mana Battery.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance(1)), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Remove any number of charge counters from Black Mana Battery: Add {B}, then add an additional {B} for each charge counter removed this way.
        ability = new DynamicManaAbility(
                Mana.BlackMana(1),
                new IntPlusDynamicValue(1, new RemovedCountersForCostValue()),
                new TapSourceCost(),
                "Add {B}, then add {B} for each charge counter removed this way",
                true, new CountersSourceCount(CounterType.CHARGE));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance(),
                "Remove any number of charge counters from {this}"));
        this.addAbility(ability);
    }

    public BlackManaBattery(final BlackManaBattery card) {
        super(card);
    }

    @Override
    public BlackManaBattery copy() {
        return new BlackManaBattery(this);
    }
}
