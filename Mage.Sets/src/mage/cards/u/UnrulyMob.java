
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author nantuko
 */
public final class UnrulyMob extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public UnrulyMob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another creature you control dies, put a +1/+1 counter on Unruly Mob.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter));
    }

    private UnrulyMob(final UnrulyMob card) {
        super(card);
    }

    @Override
    public UnrulyMob copy() {
        return new UnrulyMob(this);
    }
}
