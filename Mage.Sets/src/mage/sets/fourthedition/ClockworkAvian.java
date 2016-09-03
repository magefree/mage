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
package mage.sets.fourthedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public class ClockworkAvian extends CardImpl {

    public ClockworkAvian(UUID ownerId) {
        super(ownerId, 324, "Clockwork Avian", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "4ED";
        this.subtype.add("Bird");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Clockwork Avian enters the battlefield with four +1/+0 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P0.createInstance(4)), "{this} enters the battlefield with four +1/+0 counters on it"));
        // At end of combat, if Clockwork Avian attacked or blocked this combat, remove a +1/+0 counter from it.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkAvianEffect(), false));
        // {X}, {tap}: Put up to X +1/+0 counters on Clockwork Avian. This ability can't cause the total number of +1/+0 counters on Clockwork Avian to be greater than four. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, 
                new AvianAddCountersSourceEffect(CounterType.P1P0.createInstance(), new ManacostVariableValue(), true, true), new ManaCostsImpl("{X}"), new IsStepCondition(PhaseStep.UPKEEP), null);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ClockworkAvian(final ClockworkAvian card) {
        super(card);
    }

    @Override
    public ClockworkAvian copy() {
        return new ClockworkAvian(this);
    }
}

class AvianAddCountersSourceEffect extends AddCountersSourceEffect {

    public AvianAddCountersSourceEffect (Counter counter, DynamicValue amount, boolean informPlayers, boolean putOnCard){
        super(counter, amount, informPlayers, putOnCard);        
    }
        
    @Override
    public boolean apply(Game game, Ability source) {
        //record how many counters
        Counters permCounters = game.getPermanent(source.getSourceId()).getCounters(game);
        int countersWas = permCounters.getCount(CounterType.P1P0);
        if (countersWas < 4){
            super.apply(game, source);  
            if (permCounters.getCount(CounterType.P1P0)>4){
                permCounters.removeCounter(CounterType.P1P0, permCounters.getCount(CounterType.P1P0)-4);
            }//if countersWas < 4 then counter is min(current,4); there is no setCounters function tho        
        }//else this is a rare case of an Avian getting boosted by outside sources :) Which is the sole purpose of this if, for the benefit of this rare but not impossible case :p       
        return true;
    }
}

class ClockworkAvianEffect extends OneShotEffect {

    ClockworkAvianEffect() {
        super(Outcome.UnboostCreature);
        staticText = "remove a +1/+0 counter from {this} at end of combat";
    }

    ClockworkAvianEffect(final ClockworkAvianEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            AtTheEndOfCombatDelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(new RemoveCounterSourceEffect(CounterType.P1P0.createInstance()));
            game.addDelayedTriggeredAbility(ability, source);
        }
        return false;
    }

    @java.lang.Override
    public ClockworkAvianEffect copy() {
        return new ClockworkAvianEffect(this);
    }
}