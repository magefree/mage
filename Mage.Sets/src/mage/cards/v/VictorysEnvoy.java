package mage.cards.v;

import mage.MageInt;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
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
public final class VictorysEnvoy extends CardImpl {

    public VictorysEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, put a +1/1 counter on each other creature you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE)
        ));
    }

    private VictorysEnvoy(final VictorysEnvoy card) {
        super(card);
    }

    @Override
    public VictorysEnvoy copy() {
        return new VictorysEnvoy(this);
    }
}
