
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author Loki
 */
public final class VinelasherKudzu extends CardImpl {

    public VinelasherKudzu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PLANT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a land enters the battlefield under your control, put a +1/+1 counter on Vinelasher Kudzu.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new FilterControlledLandPermanent("a land"), false, null, true));
    }

    private VinelasherKudzu(final VinelasherKudzu card) {
        super(card);
    }

    @Override
    public VinelasherKudzu copy() {
        return new VinelasherKudzu(this);
    }
}
