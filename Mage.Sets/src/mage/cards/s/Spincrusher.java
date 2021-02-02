

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Loki
 */
public final class Spincrusher extends CardImpl {

    public Spincrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever Spincrusher blocks, put a +1/+1 counter on it.
        this.addAbility(new BlocksSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
        // Remove a +1/+1 counter from Spincrusher: Spincrusher can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1))));
    }

    private Spincrusher(final Spincrusher card) {
        super(card);
    }

    @Override
    public Spincrusher copy() {
        return new Spincrusher(this);
    }

}
