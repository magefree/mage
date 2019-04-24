
package mage.cards.s;

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
public final class SubterraneanHangar extends CardImpl {

    public SubterraneanHangar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Subterranean Hangar enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Put a storage counter on Subterranean Hangar.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new TapSourceCost()));
        // {tap}, Remove any number of storage counters from Subterranean Hangar: Add {B} for each storage counter removed this way.
        Ability ability = new DynamicManaAbility(
                Mana.BlackMana(1),
                new RemovedCountersForCostValue(),
                new TapSourceCost(),
                "Add {B} for each storage counter removed this way",
                true, new CountersSourceCount(CounterType.STORAGE));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE.createInstance(),
                "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    public SubterraneanHangar(final SubterraneanHangar card) {
        super(card);
    }

    @Override
    public SubterraneanHangar copy() {
        return new SubterraneanHangar(this);
    }
}
