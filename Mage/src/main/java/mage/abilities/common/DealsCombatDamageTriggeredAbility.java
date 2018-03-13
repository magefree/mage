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
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;

/**
 * This triggers only once for each phase the source creature deals damage.
 * So a creature blocked by two creatures and dealing damage to both blockers in the same
 * combat damage step triggers only once.
 *
 * @author LevelX
 */
public class DealsCombatDamageTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedInPhase;

    public DealsCombatDamageTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedInPhase = false;
    }

    public DealsCombatDamageTriggeredAbility(final DealsCombatDamageTriggeredAbility ability) {
            super(ability);
            this.usedInPhase = ability.usedInPhase;
    }

    @Override
    public DealsCombatDamageTriggeredAbility copy() {
            return new DealsCombatDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamagedEvent || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent
                && !usedInPhase
                && event.getSourceId().equals(this.sourceId)
                && ((DamagedEvent) event).isCombatDamage()) {
            usedInPhase = true;
            return true;

        }
        if (event.getType()== GameEvent.EventType.COMBAT_DAMAGE_STEP_PRE) {
            usedInPhase = false;
        }
        return false;
    }

    @Override
    public String getRule() {
            return "Whenever {this} deals combat damage, " + super.getRule();
    }

}
