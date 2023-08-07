package mage.cards.s;

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
public final class SaltcrustedSteppe extends CardImpl {

    public SaltcrustedSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Put a storage counter on Saltcrusted Steppe.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {1}, Remove X storage counters from Saltcrusted Steppe: Add X mana in any combination of {G} and/or {W}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD,
                new AddManaInAnyCombinationEffect(RemovedCountersForCostValue.instance,
                        new CountersSourceCount(CounterType.STORAGE), ColoredManaSymbol.G, ColoredManaSymbol.W),
                new GenericManaCost(1));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE));
        this.addAbility(ability);

    }

    private SaltcrustedSteppe(final SaltcrustedSteppe card) {
        super(card);
    }

    @Override
    public SaltcrustedSteppe copy() {
        return new SaltcrustedSteppe(this);
    }
}
