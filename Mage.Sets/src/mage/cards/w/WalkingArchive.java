
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class WalkingArchive extends CardImpl {

    public WalkingArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Walking Archive enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), "with a +1/+1 counter on it"));
        
        // At the beginning of each player's upkeep, that player draws a card for each +1/+1 counter on Walking Archive.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardTargetEffect(new CountersSourceCount(CounterType.P1P1)), TargetController.ANY, false));
        
        // {2}{W}{U}: Put a +1/+1 counter on Walking Archive.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{W}{U}")));
    }

    private WalkingArchive(final WalkingArchive card) {
        super(card);
    }

    @Override
    public WalkingArchive copy() {
        return new WalkingArchive(this);
    }
}
