
package mage.cards.a;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

/**
 *
 * @author MarcoMarin
 */
public final class ArmageddonClock extends CardImpl {

    public ArmageddonClock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of your upkeep, put a doom counter on Armageddon Clock.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.DOOM.createInstance(), StaticValue.get(1), true), TargetController.YOU, false));
        // At the beginning of your draw step, Armageddon Clock deals damage equal to the number of doom counters on it to each player.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DamagePlayersEffect(Outcome.Damage, new CountersSourceCount(CounterType.DOOM))
                .setText("{this} deals damage equal to the number of doom counters on it to each player"), TargetController.YOU, false));
        // {4}: Remove a doom counter from Armageddon Clock. Any player may activate this ability but only during any upkeep step.
        ActivatedAbilityImpl ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new RemoveCounterSourceEffect(CounterType.DOOM.createInstance()), new ManaCostsImpl<>("{4}"), new IsStepCondition(PhaseStep.UPKEEP, false),
                "Remove a doom counter from {this}. Any player may activate this ability but only during any upkeep step");

        ability.setMayActivate(TargetController.ANY);
        this.addAbility(ability);

    }

    private ArmageddonClock(final ArmageddonClock card) {
        super(card);
    }

    @Override
    public ArmageddonClock copy() {
        return new ArmageddonClock(this);
    }
}
