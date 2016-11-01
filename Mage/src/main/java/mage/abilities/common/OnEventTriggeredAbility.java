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

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class OnEventTriggeredAbility extends TriggeredAbilityImpl {

    private final EventType eventType;
    private final String eventName;
    private final boolean allPlayers;

    public OnEventTriggeredAbility(EventType eventType, String eventName, Effect effect) {
        this(eventType, eventName, effect, false);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, Effect effect, boolean optional) {
        this(eventType, eventName, false, effect, optional);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, boolean allPlayers, Effect effect) {
        this(eventType, eventName, allPlayers, effect, false);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, boolean allPlayers, Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.eventType = eventType;
        this.eventName = eventName;
        this.allPlayers = allPlayers;
    }

    public OnEventTriggeredAbility(final OnEventTriggeredAbility ability) {
        super(ability);
        this.eventType = ability.eventType;
        this.eventName = ability.eventName;
        this.allPlayers = ability.allPlayers;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == eventType;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (allPlayers || event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the " + eventName + ", " + super.getRule();
    }

    @Override
    public OnEventTriggeredAbility copy() {
        return new OnEventTriggeredAbility(this);
    }

}
