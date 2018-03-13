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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.BloodthirstWatcher;

/**
 *
 * @author spjspj
 */
public class WarElemental extends CardImpl {

    public WarElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When War Elemental enters the battlefield, sacrifice it unless an opponent was dealt damage this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(new OpponentWasDealtDamageCondition())));

        // Whenever an opponent is dealt damage, put that many +1/+1 counters on War Elemental.
        this.addAbility(new WarElementalTriggeredAbility());

    }

    public WarElemental(final WarElemental card) {
        super(card);
    }

    @Override
    public WarElemental copy() {
        return new WarElemental(this);
    }
}

class WarElementalTriggeredAbility extends TriggeredAbilityImpl {

    public WarElementalTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WarElementalEffect(), false);
    }

    public WarElementalTriggeredAbility(final WarElementalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarElementalTriggeredAbility copy() {
        return new WarElementalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt damage, put that many +1/+1 counters on {this}.";
    }
}

class WarElementalEffect extends OneShotEffect {

    public WarElementalEffect() {
        super(Outcome.Benefit);
    }

    public WarElementalEffect(final WarElementalEffect effect) {
        super(effect);
    }

    @Override
    public WarElementalEffect copy() {
        return new WarElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance((Integer) this.getValue("damageAmount"))).apply(game, source);
    }
}

class OpponentWasDealtDamageCondition implements Condition {

    public OpponentWasDealtDamageCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        BloodthirstWatcher watcher = (BloodthirstWatcher) game.getState().getWatchers().get(BloodthirstWatcher.class.getSimpleName(), source.getControllerId());
        return watcher.conditionMet();
    }

    @Override
    public String toString() {
        return "if an opponent was dealt damage this turn";
    }
}
