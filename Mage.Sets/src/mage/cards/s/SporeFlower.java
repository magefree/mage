
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class SporeFlower extends CardImpl {

    public SporeFlower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a spore counter on Spore Flower.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));

        // Remove three spore counters from Spore Flower: Prevent all combat damage that would be dealt this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true),
                new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3))));
    }

    private SporeFlower(final SporeFlower card) {
        super(card);
    }

    @Override
    public SporeFlower copy() {
        return new SporeFlower(this);
    }
}
