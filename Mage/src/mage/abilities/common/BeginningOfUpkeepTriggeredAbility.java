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

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */

public class BeginningOfUpkeepTriggeredAbility extends TriggeredAbilityImpl<BeginningOfUpkeepTriggeredAbility> {
    private Constants.TargetController targetController;
    private boolean setTargetPointer;

    public BeginningOfUpkeepTriggeredAbility(Effect effect, Constants.TargetController targetController, boolean isOptional) {
        this(Constants.Zone.BATTLEFIELD, effect, targetController, isOptional);
    }

    public BeginningOfUpkeepTriggeredAbility(Constants.Zone zone, Effect effect, Constants.TargetController targetController, boolean isOptional) {
        this(zone, effect, targetController, isOptional, true);
    }

    public BeginningOfUpkeepTriggeredAbility(Constants.Zone zone, Effect effect, Constants.TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
    }

    public BeginningOfUpkeepTriggeredAbility(final BeginningOfUpkeepTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfUpkeepTriggeredAbility copy() {
        return new BeginningOfUpkeepTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            switch (targetController) {
                case YOU:
                    boolean yours = event.getPlayerId().equals(this.controllerId);
                    if (yours && setTargetPointer) {
                        if (getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                    }
                    return yours;
                case OPPONENT:
                    if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                        if (setTargetPointer && getTargets().size() == 0) {
                            for (Effect effect : this.getEffects()) {
                                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                            }
                        }
                        return true;
                    }
            break;
                case ANY:
                    if (setTargetPointer && getTargets().size() == 0) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
                case CONTROLLER_ATTACHED_TO:
                    Permanent attachment = game.getPermanent(sourceId);
                    if (attachment != null && attachment.getAttachedTo() != null) {
                        Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                        if (attachedTo != null && attachedTo.getControllerId().equals(event.getPlayerId())) {
                            if (setTargetPointer && getTargets().size() == 0) {
                                for (Effect effect : this.getEffects()) {
                                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                                }
                            }
                            return true;
                        }
                    }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(getEffects().getText(modes.getMode()));
        switch (targetController) {
            case YOU:
                if (this.optional) {
                    if (sb.substring(0, 6).toLowerCase().equals("target")){
                        sb.insert(0, "you may have ");
                    } else if (!sb.substring(0, 4).toLowerCase().equals("you ")){
                        sb.insert(0, "you may ");
                    }
                }
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of your upkeep, ").toString();
            case OPPONENT:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each opponent's upkeep, ").toString();
            case ANY:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of each player's upkeep, ").toString();
            case CONTROLLER_ATTACHED_TO:
                return sb.insert(0, generateZoneString()).insert(0, "At the beginning of the upkeep of enchanted creature's controller, ").toString();
        }
        return "";
    }

    private String generateZoneString() {
        switch (getZone()) {
            case GRAVEYARD:
                return "if {this} is in your graveyard, ";
        }
        return "";
    }
}
