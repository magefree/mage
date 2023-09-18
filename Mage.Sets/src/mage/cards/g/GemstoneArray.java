
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class GemstoneArray extends CardImpl {

    public GemstoneArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}: Put a charge counter on Gemstone Array.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), new ManaCostsImpl<>("{2}")));
        // Remove a charge counter from Gemstone Array: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)),
                new CountersSourceCount(CounterType.CHARGE), false));
    }

    private GemstoneArray(final GemstoneArray card) {
        super(card);
    }

    @Override
    public GemstoneArray copy() {
        return new GemstoneArray(this);
    }
}
