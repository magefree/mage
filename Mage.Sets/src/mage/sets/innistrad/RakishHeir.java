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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class RakishHeir extends CardImpl<RakishHeir> {

    public RakishHeir(UUID ownerId) {
        super(ownerId, 158, "Rakish Heir", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Vampire");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new RakishHeirTriggeredAbility());
    }

    public RakishHeir(final RakishHeir card) {
        super(card);
    }

    @Override
    public RakishHeir copy() {
        return new RakishHeir(this);
    }
}

class RakishHeirTriggeredAbility extends TriggeredAbilityImpl<RakishHeirTriggeredAbility> {

    public RakishHeirTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public RakishHeirTriggeredAbility(final RakishHeirTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RakishHeirTriggeredAbility copy() {
        return new RakishHeirTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && permanent != null
                    && permanent.hasSubtype("Vampire") && permanent.getControllerId().equals(controllerId)) {
                this.getEffects().clear();
                AddCountersTargetEffect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }
    	
    @Override
    public String getRule() {
        return "Whenever a Vampire you control deals combat damage to a player, put a +1/+1 counter on it.";
    }
}
