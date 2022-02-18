
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class PalliationAccord extends CardImpl {

    public PalliationAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");

        // Whenever a creature an opponent controls becomes tapped, put a shield counter on Palliation Accord.
        this.addAbility(new BecomesTappedTriggeredAbility(new AddCountersSourceEffect(CounterType.SHIELD.createInstance()), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));

        // Remove a shield counter from Palliation Accord: Prevent the next 1 damage that would be dealt to you this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToControllerEffect(Duration.EndOfTurn, 1),
                new RemoveCountersSourceCost(CounterType.SHIELD.createInstance())));
    }

    private PalliationAccord(final PalliationAccord card) {
        super(card);
    }

    @Override
    public PalliationAccord copy() {
        return new PalliationAccord(this);
    }
}
