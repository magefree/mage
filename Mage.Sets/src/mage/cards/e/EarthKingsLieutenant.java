package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EarthKingsLieutenant extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.ALLY);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.ALLY, "another Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public EarthKingsLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, put a +1/+1 counter on each other Ally creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
                        .setText("put a +1/+1 counter on each other Ally creature you control")
        ));

        // Whenever another Ally you control enters, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter2
        ));
    }

    private EarthKingsLieutenant(final EarthKingsLieutenant card) {
        super(card);
    }

    @Override
    public EarthKingsLieutenant copy() {
        return new EarthKingsLieutenant(this);
    }
}
