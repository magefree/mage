package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class MoltenSlagheap extends CardImpl {

    public MoltenSlagheap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Put a storage counter on Molten Slagheap.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {1}, Remove X storage counters from Molten Slagheap: Add X mana in any combination of {B} and/or {R}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(RemovedCountersForCostValue.instance,
                        new CountersSourceCount(CounterType.STORAGE), ColoredManaSymbol.B, ColoredManaSymbol.R),
                new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE));
        this.addAbility(ability);
    }

    private MoltenSlagheap(final MoltenSlagheap card) {
        super(card);
    }

    @Override
    public MoltenSlagheap copy() {
        return new MoltenSlagheap(this);
    }
}
