
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 *
 */
public final class MageRingNetwork extends CardImpl {

    public MageRingNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {T}: Put a storage counter on Mage-Ring Network.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()),
                new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {T}, Remove any number of storage counters from Mage-Ring Network: Add {C} for each storage counter removed this way.
        ability = new DynamicManaAbility(
                Mana.ColorlessMana(1),
                RemovedCountersForCostValue.instance,
                new TapSourceCost(),
                "Add {C} for each storage counter removed this way",
                true, new CountersSourceCount(CounterType.STORAGE));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE,
                "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    private MageRingNetwork(final MageRingNetwork card) {
        super(card);
    }

    @Override
    public MageRingNetwork copy() {
        return new MageRingNetwork(this);
    }
}
