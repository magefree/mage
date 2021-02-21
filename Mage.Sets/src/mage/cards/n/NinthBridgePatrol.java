
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class NinthBridgePatrol extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public NinthBridgePatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control leaves the battlefield, put a +1/+1 counter on Ninth Bridge Patrol.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, null,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter,
                "Whenever another creature you control leaves the battlefield, ", false));
    }

    private NinthBridgePatrol(final NinthBridgePatrol card) {
        super(card);
    }

    @Override
    public NinthBridgePatrol copy() {
        return new NinthBridgePatrol(this);
    }
}
