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
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public class DealsDamageToOpponentTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean onlyCombat, setTargetPointer;

    public DealsDamageToOpponentTriggeredAbility(Effect effect) {
        this(effect, false, false, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional, boolean onlyCombat) {
        this(effect, optional, onlyCombat, false);
    }

    public DealsDamageToOpponentTriggeredAbility(Effect effect, boolean optional, boolean onlyCombat, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.onlyCombat = onlyCombat;
        this.setTargetPointer = setTargetPointer;
    }

    public DealsDamageToOpponentTriggeredAbility(final DealsDamageToOpponentTriggeredAbility ability) {
        super(ability);
        this.onlyCombat = ability.onlyCombat;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToOpponentTriggeredAbility copy() {
        return new DealsDamageToOpponentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId)
                && game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            if (onlyCombat && event instanceof DamagedPlayerEvent) {
                DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
                if (!damageEvent.isCombatDamage()) {
                    return false;
                }
            }
            if(setTargetPointer) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    effect.setValue("damage", event.getAmount());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Whenever {this} deals ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage to an opponent, ").append(super.getRule());
        return sb.toString();
    }
}
