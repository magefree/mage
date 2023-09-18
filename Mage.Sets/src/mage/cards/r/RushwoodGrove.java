
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
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
 * @author anonymous
 */
public final class RushwoodGrove extends CardImpl {

    public RushwoodGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Rushwood Grove enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Put a storage counter on Rushwood Grove.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new TapSourceCost()));
        // {T}, Remove any number of storage counters from Rushwood Grove: Add {G} for each storage counter removed this way.
        Ability ability = new DynamicManaAbility(
                Mana.GreenMana(1),
                RemovedCountersForCostValue.instance,
                new TapSourceCost(),
                "Add {G} for each storage counter removed this way",
                true, new CountersSourceCount(CounterType.STORAGE));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE,
                "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    private RushwoodGrove(final RushwoodGrove card) {
        super(card);
    }

    @Override
    public RushwoodGrove copy() {
        return new RushwoodGrove(this);
    }
}
