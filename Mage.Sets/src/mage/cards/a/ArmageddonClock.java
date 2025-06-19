package mage.cards.a;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class ArmageddonClock extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.UPKEEP, false);

    public ArmageddonClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of your upkeep, put a doom counter on Armageddon Clock.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.DOOM.createInstance())));

        // At the beginning of your draw step, Armageddon Clock deals damage equal to the number of doom counters on it to each player.
        this.addAbility(new BeginningOfDrawTriggeredAbility(
                new DamagePlayersEffect(Outcome.Damage, new CountersSourceCount(CounterType.DOOM))
                        .setText("{this} deals damage equal to the number of doom counters on it to each player"), false
        ));

        // {4}: Remove a doom counter from Armageddon Clock. Any player may activate this ability but only during any upkeep step.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new RemoveCounterSourceEffect(CounterType.DOOM.createInstance()), new GenericManaCost(4), condition
        ).withConditionText("any player may activate this ability but only during any upkeep step").setMayActivate(TargetController.ANY));
    }

    private ArmageddonClock(final ArmageddonClock card) {
        super(card);
    }

    @Override
    public ArmageddonClock copy() {
        return new ArmageddonClock(this);
    }
}
