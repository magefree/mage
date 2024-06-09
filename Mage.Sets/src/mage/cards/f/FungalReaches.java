package mage.cards.f;

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
public final class FungalReaches extends CardImpl {

    public FungalReaches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {tap}: Put a storage counter on Fungal Reaches.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {1}, Remove X storage counters from Fungal Reaches: Add X mana in any combination of {R} and/or {G}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(RemovedCountersForCostValue.instance,
                        new CountersSourceCount(CounterType.STORAGE), ColoredManaSymbol.R, ColoredManaSymbol.G),
                new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE));
        this.addAbility(ability);

    }

    private FungalReaches(final FungalReaches card) {
        super(card);
    }

    @Override
    public FungalReaches copy() {
        return new FungalReaches(this);
    }
}
