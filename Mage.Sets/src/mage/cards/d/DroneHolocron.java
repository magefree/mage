
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class DroneHolocron extends CardImpl {

    public DroneHolocron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Put a charge counter on Drone Holocron.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new TapSourceCost()));

        // {T}, Remove a charge counter from Drone Holocron: Add {W}, {U} or {B}.
        Cost cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1));
        Ability ability = new WhiteManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new BlueManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new BlackManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        // {T}, Remove two charge counters from Drone Holocron: Add WU or UB.
        cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(2));

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 0, 0, 0, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 1, 0, 0, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);
    }

    private DroneHolocron(final DroneHolocron card) {
        super(card);
    }

    @Override
    public DroneHolocron copy() {
        return new DroneHolocron(this);
    }
}
