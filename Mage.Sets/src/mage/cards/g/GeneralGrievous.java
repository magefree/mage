
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class GeneralGrievous extends CardImpl {

    public GeneralGrievous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KALEESH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double Strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever another creature dies, you may put a +1/+1 counter on General Grievous.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, true));

        // Remove two +1/+1 counters from General Grievous: Return General Grievous to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2))));
    }

    private GeneralGrievous(final GeneralGrievous card) {
        super(card);
    }

    @Override
    public GeneralGrievous copy() {
        return new GeneralGrievous(this);
    }
}
