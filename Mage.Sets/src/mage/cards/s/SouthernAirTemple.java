package mage.cards.s;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SouthernAirTemple extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SHRINE));
    private static final Hint hint = new ValueHint("Shrines you control", xValue);
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE, "another Shrine you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SouthernAirTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Southern Air Temple enters, put X +1/+1 counters on each creature you control, where X is the number of Shrines you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), xValue, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("put X +1/+1 counters on each creature you control, where X is the number of Shrines you control")).addHint(hint));

        // Whenever another Shrine you control enters, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), filter));
    }

    private SouthernAirTemple(final SouthernAirTemple card) {
        super(card);
    }

    @Override
    public SouthernAirTemple copy() {
        return new SouthernAirTemple(this);
    }
}
