
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
public final class CalciformPools extends CardImpl {

    public CalciformPools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Put a storage counter on Calciform Pools.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {1}, Remove X storage counters from Calciform Pools: Add X mana in any combination of {W} and/or {U}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(new RemovedCountersForCostValue(), ColoredManaSymbol.W, ColoredManaSymbol.U),
                new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE.createInstance()));
        this.addAbility(ability);

    }

    public CalciformPools(final CalciformPools card) {
        super(card);
    }

    @Override
    public CalciformPools copy() {
        return new CalciformPools(this);
    }
}
