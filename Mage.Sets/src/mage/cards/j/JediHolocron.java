
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
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
public final class JediHolocron extends CardImpl {

    public JediHolocron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {T}: Put a charge counter on Jedi Holocron.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new TapSourceCost()));

        // {T}, Remove a charge counter from Jedi Holocron: Add {G}, {W} or {U}.
        Cost cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1));
        Ability ability = new GreenManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new WhiteManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new BlueManaAbility();
        ability.addCost(cost);
        this.addAbility(ability);

        // {T}, Remove two charge counters from Jedi Holocron: Add GW or WU.
        cost = new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(2));

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 0, 1, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 0, 0, 0, 0, 0, 0), new TapSourceCost());
        ability.addCost(cost);
        this.addAbility(ability);
    }

    private JediHolocron(final JediHolocron card) {
        super(card);
    }

    @Override
    public JediHolocron copy() {
        return new JediHolocron(this);
    }
}
