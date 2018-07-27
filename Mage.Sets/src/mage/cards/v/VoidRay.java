package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;

/**
 *
 * @author NinthWorld
 */
public final class VoidRay extends CardImpl {

    public VoidRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Void Ray deals combat damage to a player, put a psi counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.PSI.createInstance()), false));

        // Void Ray gets +1/+1 for each psi counter on it.
        CountersSourceCount count = new CountersSourceCount(CounterType.PSI);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));
    }

    public VoidRay(final VoidRay card) {
        super(card);
    }

    @Override
    public VoidRay copy() {
        return new VoidRay(this);
    }
}
