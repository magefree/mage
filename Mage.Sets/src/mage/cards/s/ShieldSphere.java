
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author dustinconrad
 */
public final class ShieldSphere extends CardImpl {

    public ShieldSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{0}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Shield Sphere blocks, put a -0/-1 counter on it.
        this.addAbility(new BlocksSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.M0M1.createInstance()), false));
    }

    private ShieldSphere(final ShieldSphere card) {
        super(card);
    }

    @Override
    public ShieldSphere copy() {
        return new ShieldSphere(this);
    }
}
