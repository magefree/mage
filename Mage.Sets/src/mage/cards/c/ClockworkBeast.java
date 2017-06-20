/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ClockworkBeast extends CardImpl {

    public ClockworkBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add("Beast");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Clockwork Beast enters the battlefield with seven +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P0.createInstance(7)), "{this} enters the battlefield with seven +1/+0 counters on it"));

        // At end of combat, if Clockwork Beast attacked or blocked this combat, remove a +1/+0 counter from it.
        this.addAbility(new ConditionalTriggeredAbility(
                new EndOfCombatTriggeredAbility(new RemoveCounterSourceEffect(CounterType.P1P0.createInstance()), false),
                AttackedOrBlockedThisCombatSourceCondition.instance,
                "At end of combat, if {this} attacked or blocked this combat, remove a +1/+0 counter from it."),
                new AttackedOrBlockedThisCombatWatcher());

        // {X}, {tap}: Put up to X +1/+0 counters on Clockwork Beast. This ability can't cause the total number of +1/+0 counters on Clockwork Beast to be greater than seven. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new BeastAddCountersSourceEffect(CounterType.P1P0.createInstance(), new ManacostVariableValue(), true, true), new ManaCostsImpl("{X}"), new IsStepCondition(PhaseStep.UPKEEP), null);
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
