package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerraRedeemer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SerraRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature with power 2 or less enters the battlefield under your control, put two +1/+1 counters on that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                filter, false, SetTargetPointer.PERMANENT, null
        ));
    }

    private SerraRedeemer(final SerraRedeemer card) {
        super(card);
    }

    @Override
    public SerraRedeemer copy() {
        return new SerraRedeemer(this);
    }
}
