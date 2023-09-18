package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class MidnightBanshee extends CardImpl {

    public MidnightBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(WitherAbility.getInstance());
        // At the beginning of your upkeep, put a -1/-1 counter on each nonblack creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAllEffect(CounterType.M1M1.createInstance(), StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK), TargetController.YOU, false));
    }

    private MidnightBanshee(final MidnightBanshee card) {
        super(card);
    }

    @Override
    public MidnightBanshee copy() {
        return new MidnightBanshee(this);
    }
}
