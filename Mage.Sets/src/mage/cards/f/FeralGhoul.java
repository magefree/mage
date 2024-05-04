package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FeralGhoul extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public FeralGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another creature you control dies, put a +1/+1 counter on Feral Ghoul.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // When Feral Ghoul dies, each opponent gets a number of rad counters equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(), xValue, TargetController.OPPONENT)
                        .setText("each opponent gets a number of rad counters equal to its power")
        ));
    }

    private FeralGhoul(final FeralGhoul card) {
        super(card);
    }

    @Override
    public FeralGhoul copy() {
        return new FeralGhoul(this);
    }
}
