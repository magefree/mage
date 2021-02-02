package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.keyword.SunburstAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class PentadPrism extends CardImpl {

    public PentadPrism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove a charge counter from Pentad Prism: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)),
                new CountersSourceCount(CounterType.CHARGE), false));
    }

    private PentadPrism(final PentadPrism card) {
        super(card);
    }

    @Override
    public PentadPrism copy() {
        return new PentadPrism(this);
    }
}
