package mage.cards.o;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ouroboroid extends CardImpl {

    public Ouroboroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, put X +1/+1 counters on each creature you control, where X is this creature's power.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), SourcePermanentPowerValue.NOT_NEGATIVE,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("put X +1/+1 counters on each creature you control, where X is {this}'s power")));
    }

    private Ouroboroid(final Ouroboroid card) {
        super(card);
    }

    @Override
    public Ouroboroid copy() {
        return new Ouroboroid(this);
    }
}
