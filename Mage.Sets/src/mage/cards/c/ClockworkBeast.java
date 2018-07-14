
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ClockworkBeast extends CardImpl {

    public ClockworkBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Clockwork Beast enters the battlefield with seven +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P0.createInstance(7)),
                "with seven +1/+0 counters on it"
        ));

        // At end of combat, if Clockwork Beast attacked or blocked this combat, remove a +1/+0 counter from it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EndOfCombatTriggeredAbility(new RemoveCounterSourceEffect(CounterType.P1P0.createInstance()), false),
                AttackedOrBlockedThisCombatSourceCondition.instance,
                "At end of combat, if {this} attacked or blocked this combat, remove a +1/+0 counter from it."),
                new AttackedOrBlockedThisCombatWatcher()
        );

        // {X}, {tap}: Put up to X +1/+0 counters on Clockwork Beast. This ability can't cause the total number of +1/+0 counters on Clockwork Beast to be greater than seven. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new BeastAddCountersSourceEffect(
                        CounterType.P1P0.createInstance(),
                        new ManacostVariableValue(),
                        true, true
                ),
                new ManaCostsImpl("{X}"),
                new IsStepCondition(PhaseStep.UPKEEP),
                null
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ClockworkBeast(final ClockworkBeast card) {
        super(card);
    }

    @Override
    public ClockworkBeast copy() {
        return new ClockworkBeast(this);
    }
}

class BeastAddCountersSourceEffect extends AddCountersSourceEffect {

    public BeastAddCountersSourceEffect(Counter counter, DynamicValue amount, boolean informPlayers, boolean putOnCard) {
        super(counter, amount, informPlayers, putOnCard);
        staticText = "Put up to X +1/+0 counters on {this}. This ability can't cause the total number of +1/+0 counters on {this} to be greater than seven.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Counters permCounters = game.getPermanent(source.getSourceId()).getCounters(game);
        int countersWas = permCounters.getCount(CounterType.P1P0);
        if (countersWas < 7) {
            super.apply(game, source);
            if (permCounters.getCount(CounterType.P1P0) > 7) {
                permCounters.removeCounter(CounterType.P1P0, permCounters.getCount(CounterType.P1P0) - 7);
            }//if countersWas < 7 then counter is min(current,7); there is no setCounters function though
        }//else this is a rare case of a Beast getting boosted by outside sources. Which is the sole purpose of this if, for the benefit of this rare but not impossible case
        return true;
    }

    public BeastAddCountersSourceEffect(final BeastAddCountersSourceEffect effect) {
        super(effect);
    }

    @Override
    public BeastAddCountersSourceEffect copy() {
        return new BeastAddCountersSourceEffect(this);
    }
}
