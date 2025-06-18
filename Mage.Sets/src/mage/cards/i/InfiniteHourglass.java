package mage.cards.i;

import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfiniteHourglass extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.TIME);
    private static final Condition condition = new IsStepCondition(PhaseStep.UPKEEP, false);

    public InfiniteHourglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, put a time counter on Infinite Hourglass.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance())));

        // All creatures get +1/+0 for each time counter on Infinite Hourglass.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // {3}: Remove a time counter from Infinite Hourglass. Any player may activate this ability but only during any upkeep step.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new RemoveCounterSourceEffect(CounterType.TIME.createInstance()), new GenericManaCost(3), condition
        ).withConditionText("any player may activate this ability but only during any upkeep step").setMayActivate(TargetController.ANY));
    }

    private InfiniteHourglass(final InfiniteHourglass card) {
        super(card);
    }

    @Override
    public InfiniteHourglass copy() {
        return new InfiniteHourglass(this);
    }
}
