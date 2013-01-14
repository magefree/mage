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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class FiveAlarmFire extends CardImpl<FiveAlarmFire> {

    public FiveAlarmFire(UUID ownerId) {
        super(ownerId, 91, "Five-Alarm Fire", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");
        this.expansionSetCode = "GTC";
        
        this.color.setRed(true);

        //Whenever a creature you control deals combat damage, put a blaze counter on Five-Alarm Fire.
        this.addAbility(new FiveAlarmFireTriggeredAbility());
        //Remove five blaze counters from Five-Alarm Fire: Five-Alarm Fire deals 5 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(5), new RemoveCountersSourceCost(CounterType.BLAZE.createInstance(5)));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public FiveAlarmFire(final FiveAlarmFire card) {
        super(card);
    }

    @Override
    public FiveAlarmFire copy() {
        return new FiveAlarmFire(this);
    }
}


class FiveAlarmFireTriggeredAbility extends TriggeredAbilityImpl<FiveAlarmFireTriggeredAbility> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    public FiveAlarmFireTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.BLAZE.createInstance()), false);
    }

    public FiveAlarmFireTriggeredAbility(final FiveAlarmFireTriggeredAbility ability) {
            super(ability);
    }

    @Override
    public FiveAlarmFireTriggeredAbility copy() {
            return new FiveAlarmFireTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER || event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
                Permanent permanent = game.getPermanent(event.getSourceId());
                if(permanent != null &&  filter.match(permanent, sourceId, controllerId, game) && ((DamagedEvent) event).isCombatDamage()){
                    return true;
                }
            }
            return false;
    }

    @Override
    public String getRule() {
            return "Whenever a creature you control deals combat damage, " + super.getRule();
    }

}
