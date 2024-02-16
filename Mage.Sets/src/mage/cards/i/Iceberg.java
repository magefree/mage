
package mage.cards.i;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class Iceberg extends CardImpl {

    public Iceberg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{U}{U}");

        // Iceberg enters the battlefield with X ice counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.ICE.createInstance())));

        // {3}: Put an ice counter on Iceberg.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.ICE.createInstance(1)), new ManaCostsImpl<>("{3}")));

        // Remove an ice counter from Iceberg: Add {C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), 
                new RemoveCountersSourceCost(CounterType.ICE.createInstance(1)),
                new CountersSourceCount(CounterType.ICE)));
    }

    private Iceberg(final Iceberg card) {
        super(card);
    }

    @Override
    public Iceberg copy() {
        return new Iceberg(this);
    }
}
