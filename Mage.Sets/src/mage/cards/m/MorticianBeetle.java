package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class MorticianBeetle extends CardImpl {

    public MorticianBeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Mortician Beetle.
        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_PERMANENT_CREATURE,
                TargetController.ANY, SetTargetPointer.NONE, true));
    }

    private MorticianBeetle(final MorticianBeetle card) {
        super(card);
    }

    @Override
    public MorticianBeetle copy() {
        return new MorticianBeetle(this);
    }
}
