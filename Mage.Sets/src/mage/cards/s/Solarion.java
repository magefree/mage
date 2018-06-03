
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class Solarion extends CardImpl {

    public Solarion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // {tap}: Double the number of +1/+1 counters on Solarion.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(1), new CountersSourceCount(CounterType.P1P1), true);
        effect.setText("Double the number of +1/+1 counters on {this}");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost()));
    }

    public Solarion(final Solarion card) {
        super(card);
    }

    @Override
    public Solarion copy() {
        return new Solarion(this);
    }
}
