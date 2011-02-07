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

package mage.sets.mirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class ClockworkDragon extends CardImpl<ClockworkDragon> {

    public ClockworkDragon (UUID ownerId) {
        super(ownerId, 155, "Clockwork Dragon", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Dragon");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(6)), "Clockwork Dragon enters the battlefield with six +1/+1 counters on it"));
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new ClockworkDragonEffect(), false));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(3)));
    }

    public ClockworkDragon (final ClockworkDragon card) {
        super(card);
    }

    @Override
    public ClockworkDragon copy() {
        return new ClockworkDragon(this);
    }
}

class ClockworkDragonEffect extends OneShotEffect<ClockworkDragonEffect> {
    ClockworkDragonEffect() {
        super(Constants.Outcome.UnboostCreature);
    }

    ClockworkDragonEffect(final ClockworkDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            ClockworkDragonDelayedTriggeredAbility delayedAbility = new ClockworkDragonDelayedTriggeredAbility();
            delayedAbility.setSourceId(source.getSourceId());
			delayedAbility.setControllerId(source.getControllerId());
			game.addDelayedTriggeredAbility(delayedAbility);
        }
        return false;
    }

    @Override
    public ClockworkDragonEffect copy() {
        return new ClockworkDragonEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "remove a +1/+1 counter from Clockwork Dragon at end of combat";
    }
}

class ClockworkDragonDelayedTriggeredAbility extends DelayedTriggeredAbility<ClockworkDragonDelayedTriggeredAbility> {
    ClockworkDragonDelayedTriggeredAbility() {
        super(new RemoveCounterSourceEffect(CounterType.P1P1.createInstance()));
    }

    ClockworkDragonDelayedTriggeredAbility(final ClockworkDragonDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ClockworkDragonDelayedTriggeredAbility copy() {
        return new ClockworkDragonDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COMBAT_PHASE_POST) {
            return true;
        }
        return false;
    }
}