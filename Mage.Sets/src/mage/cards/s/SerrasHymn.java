package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author jeffwadsworth
 */
public final class SerrasHymn extends CardImpl {

    private static final String rule = "Prevent the next X damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose, where X is the number of verse counters on {this}.";

    public SerrasHymn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // At the beginning of your upkeep, you may put a verse counter on Serra's Hymn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // Sacrifice Serra's Hymn: Prevent the next X damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose, where X is the number of verse counters on Serra's Hymn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventDamageToTargetMultiAmountEffect(
                        Duration.EndOfTurn,
                        1, false, true, // the integer 1 is ignored due to the dynamic number being set
                        new CountersSourceCount(CounterType.VERSE)).setText(rule),
                new SacrificeSourceCost());
        ability.addTarget(new TargetAnyTargetAmount(new CountersSourceCount(CounterType.VERSE)));
        this.addAbility(ability);

    }

    private SerrasHymn(final SerrasHymn card) {
        super(card);
    }

    @Override
    public SerrasHymn copy() {
        return new SerrasHymn(this);
    }
}
