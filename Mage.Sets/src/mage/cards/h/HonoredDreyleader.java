package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HonoredDreyleader extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another Squirrel or Food");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.SQUIRREL.getPredicate(),
                SubType.FOOD.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Other Squirrels and Food you control", xValue);

    public HonoredDreyleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Honored Dreyleader enters, put a +1/+1 counter on it for each other Squirrel and/or Food you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), xValue)
                        .setText("put a +1/+1 counter on it for each other Squirrel and/or Food you control")
        ).addHint(hint));

        // Whenever another Squirrel or Food you control enters, put a +1/+1 counter on Honored Dreyleader.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private HonoredDreyleader(final HonoredDreyleader card) {
        super(card);
    }

    @Override
    public HonoredDreyleader copy() {
        return new HonoredDreyleader(this);
    }
}
