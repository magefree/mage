package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BoundingFelidar extends CardImpl {

    private final static FilterPermanent filter = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private final static DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private final static Hint hint = new ValueHint("Other creatures you control", xValue);

    public BoundingFelidar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Whenever Bounding Felidar attacks while saddled, put a +1/+1 counter on each other creature you control. You gain 1 life for each of those creatures.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
        ability.addEffect(new GainLifeEffect(xValue).setText("You gain 1 life for each of those creatures"));
        ability.addHint(hint);
        this.addAbility(ability);

        // Saddle 2
        this.addAbility(new SaddleAbility(2));
    }

    private BoundingFelidar(final BoundingFelidar card) {
        super(card);
    }

    @Override
    public BoundingFelidar copy() {
        return new BoundingFelidar(this);
    }
}
