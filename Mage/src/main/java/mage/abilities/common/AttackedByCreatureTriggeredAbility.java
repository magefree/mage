/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AttackedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected SetTargetPointer setTargetPointer;

    public AttackedByCreatureTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttackedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public AttackedByCreatureTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, setTargetPointer);
    }

    public AttackedByCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public AttackedByCreatureTriggeredAbility(final AttackedByCreatureTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID playerId = game.getCombat().getDefendingPlayerId(event.getSourceId(), game);
        Permanent attackingCreature = game.getPermanent(event.getSourceId());
        if (getControllerId().equals(playerId) && attackingCreature != null) {
            if (!setTargetPointer.equals(SetTargetPointer.NONE)) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case PERMANENT:
                            effect.setTargetPointer(new FixedTarget(attackingCreature.getId()));
                            break;
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(attackingCreature.getControllerId()));
                            break;
                    }

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AttackedByCreatureTriggeredAbility copy() {
        return new AttackedByCreatureTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks you, " + super.getRule();
    }

}
