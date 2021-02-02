
package mage.cards.i;

import java.util.UUID;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class InfiniteHourglass extends CardImpl {

    public InfiniteHourglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, put a time counter on Infinite Hourglass.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance()), TargetController.YOU, false));

        // All creatures get +1/+0 for each time counter on Infinite Hourglass.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostAllEffect(
                        new CountersSourceCount(CounterType.TIME),
                        StaticValue.get(0),
                        Duration.WhileOnBattlefield
                )
        ));

        // {3}: Remove a time counter from Infinite Hourglass. Any player may activate this ability but only during any upkeep step.
        ActivatedAbility ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new RemoveCounterSourceEffect(CounterType.TIME.createInstance()),
                new GenericManaCost(3),
                new IsStepCondition(PhaseStep.UPKEEP, false)
        );
        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);
    }

    private InfiniteHourglass(final InfiniteHourglass card) {
        super(card);
    }

    @Override
    public InfiniteHourglass copy() {
        return new InfiniteHourglass(this);
    }
}
