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
package mage.abilities;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TriggeredAbilityImpl extends AbilityImpl implements TriggeredAbility {

    protected boolean optional;
    protected boolean leavesTheBattlefieldTrigger;

    public TriggeredAbilityImpl(Zone zone, Effect effect) {
        this(zone, effect, false);
    }

    public TriggeredAbilityImpl(Zone zone, Effect effect, boolean optional) {
        super(AbilityType.TRIGGERED, zone);
        setLeavesTheBattlefieldTrigger(false);
        if (effect != null) {
            addEffect(effect);
        }
        this.optional = optional;
    }

    public TriggeredAbilityImpl(final TriggeredAbilityImpl ability) {
        super(ability);
        this.optional = ability.optional;
        this.leavesTheBattlefieldTrigger = ability.leavesTheBattlefieldTrigger;
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        //20091005 - 603.4
        if (!(this instanceof DelayedTriggeredAbility)) {
            setSourceObject(null, game);
        }
        if (checkInterveningIfClause(game)) {
            game.addTriggeredAbility(this);
        }
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return true;
    }

    @Override
    public boolean resolve(Game game) {
        if (isOptional()) {
            MageObject object = game.getObject(getSourceId());
            Player player = game.getPlayer(this.getControllerId());
            if (player != null && object != null) {
                if (!player.chooseUse(getEffects().get(0).getOutcome(), (object != null ? this.getRule(object.getLogName()) : this.getRule()), this, game)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        //20091005 - 603.4
        if (checkInterveningIfClause(game)) {
            return super.resolve(game);
        }
        return false;
    }

    @Override
    public String getGameLogMessage(Game game) {
        MageObject object = game.getObject(sourceId);
        StringBuilder sb = new StringBuilder();
        if (object != null) {
            sb.append("Ability triggers: ").append(object.getLogName()).append(" - ").append(this.getRule(object.getLogName()));
        } else {
            sb.append("Ability triggers: ").append(this.getRule());
        }
        String targetText = getTargetDescriptionForLog(getTargets(), game);
        if (!targetText.isEmpty()) {
            sb.append(" - ").append(targetText);
        }
        return sb.toString();
    }

    @Override
    public String getRule() {
        String superRule = super.getRule(true);
        StringBuilder sb = new StringBuilder();
        if (!superRule.isEmpty()) {
            String ruleLow = superRule.toLowerCase();
            if (isOptional()) {
                if (ruleLow.startsWith("you ")) {
                    if (!ruleLow.startsWith("you may")) {
                        StringBuilder newRule = new StringBuilder(superRule);
                        newRule.insert(4, "may ");
                        superRule = newRule.toString();
                    }
                } else if (this.getTargets().isEmpty()
                        || ruleLow.startsWith("exile")
                        || ruleLow.startsWith("destroy")
                        || ruleLow.startsWith("return")
                        || ruleLow.startsWith("tap")
                        || ruleLow.startsWith("untap")
                        || ruleLow.startsWith("put")
                        || ruleLow.startsWith("remove")
                        || ruleLow.startsWith("counter")) {
                    sb.append("you may ");
                } else if (!ruleLow.startsWith("its controller may")) {
                    sb.append("you may have ");
                }

            }
            sb.append(superRule);
        }

        return sb.toString();
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {

        /**
         * 603.6. Trigger events that involve objects changing zones are called
         * “zone-change triggers.” Many abilities with zone-change triggers
         * attempt to do something to that object after it changes zones. During
         * resolution, these abilities look for the object in the zone that it
         * moved to. If the object is unable to be found in the zone it went to,
         * the part of the ability attempting to do something to the object will
         * fail to do anything. The ability could be unable to find the object
         * because the object never entered the specified zone, because it left
         * the zone before the ability resolved, or because it is in a zone that
         * is hidden from a player, such as a library or an opponent’s hand.
         * (This rule applies even if the object leaves the zone and returns
         * again before the ability resolves.) The most common zone-change
         * triggers are enters-the-battlefield triggers and
         * leaves-the-battlefield triggers.
         *
         * from:
         * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/537065-ixidron-and-kozilek
         * There are two types of triggers that involve the graveyard: dies
         * triggers (which are a subset of leave-the-battlefield triggers) and
         * put into the graveyard from anywhere triggers.
         *
         * The former triggers trigger based on the game state prior to the move
         * where the Kozilek permanent is face down and has no abilities. The
         * latter triggers trigger from the game state after the move where the
         * Kozilek card is itself and has the ability.
         */
        if (event != null && event.getTargetId() != null && event.getTargetId().equals(getSourceId())) {
            switch (event.getType()) {
                case ZONE_CHANGE:
                case DESTROYED_PERMANENT:
                    if (isLeavesTheBattlefieldTrigger()) {
                        if (event.getType().equals(EventType.DESTROYED_PERMANENT)) {
                            source = game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD);
                        } else if (((ZoneChangeEvent) event).getTarget() != null) {
                            source = ((ZoneChangeEvent) event).getTarget();
                        } else {
                            source = game.getLastKnownInformation(getSourceId(), ((ZoneChangeEvent) event).getZone());
                        }
                    }

                case PHASED_OUT:
                case PHASED_IN:
                    if (this.zone == Zone.ALL || game.getLastKnownInformation(getSourceId(), zone) != null) {
                        return this.hasSourceObjectAbility(game, source, event);
                    }
            }
        }
        return super.isInUseableZone(game, source, event);
    }

    /*
     603.6c,603.6d
     */
    @Override
    public boolean isLeavesTheBattlefieldTrigger() {
        return leavesTheBattlefieldTrigger;
    }

    /*
     603.6c,603.6d
     This has to be set, if the triggered ability has to check back in time if the permanent the ability is connected to had the ability on the battlefeild while the trigger is checked
     */
    @Override
    public final void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger) {
        this.leavesTheBattlefieldTrigger = leavesTheBattlefieldTrigger;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

}
