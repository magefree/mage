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
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Lonefox
 */
public class SacrificeIfCastAtInstantTimeTriggeredAbility extends TriggeredAbilityImpl {

    public SacrificeIfCastAtInstantTimeTriggeredAbility() {
        super(Zone.STACK, new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(new SacrificeSourceEffect())));
    }

    public SacrificeIfCastAtInstantTimeTriggeredAbility(final SacrificeIfCastAtInstantTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SacrificeIfCastAtInstantTimeTriggeredAbility copy() {
        return new SacrificeIfCastAtInstantTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // The sacrifice occurs only if you cast it using its own ability. If you cast it using some other
        // effect (for instance, if it gained flash from Vedalken Orrery), then it won't be sacrificed.
        // CHECK
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getSourceId().equals(getSourceId())) {
            return !(game.isMainPhase() && game.getActivePlayerId().equals(event.getPlayerId()) && game.getStack().size() == 1);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "If you cast it any time a sorcery couldn't have been cast, the controller of the permanent it becomes sacrifices it at the beginning of the next cleanup step.";
    }
}

