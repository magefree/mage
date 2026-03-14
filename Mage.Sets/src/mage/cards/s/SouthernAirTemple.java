package mage.cards.s;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ShrinesYouControlCount;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SouthernAirTemple extends CardImpl {

    public SouthernAirTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // When Southern Air Temple enters, put X +1/+1 counters on each creature you control, where X is the number of Shrines you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), ShrinesYouControlCount.WHERE_X, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("put X +1/+1 counters on each creature you control, where X is the number of Shrines you control")).addHint(ShrinesYouControlCount.getHint()));

        // Whenever another Shrine you control enters, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), StaticFilters.FILTER_ANOTHER_CONTROLLED_SHRINE));
    }

    private SouthernAirTemple(final SouthernAirTemple card) {
        super(card);
    }

    @Override
    public SouthernAirTemple copy() {
        return new SouthernAirTemple(this);
    }
}
