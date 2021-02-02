
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class SawtoothThresher extends CardImpl {

    public SawtoothThresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove two +1/+1 counters from Sawtooth Thresher: Sawtooth Thresher gets +4/+4 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn), new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2))));
    }

    private SawtoothThresher(final SawtoothThresher card) {
        super(card);
    }

    @Override
    public SawtoothThresher copy() {
        return new SawtoothThresher(this);
    }
}
