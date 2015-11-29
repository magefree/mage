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
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class DealsDamageToAPlayerAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;
    private final boolean onlyCombat;

    public DealsDamageToAPlayerAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        this.onlyCombat = onlyCombat;
    }

    public DealsDamageToAPlayerAllTriggeredAbility(final DealsDamageToAPlayerAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
    }

    @Override
    public DealsDamageToAPlayerAllTriggeredAbility copy() {
        return new DealsDamageToAPlayerAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!onlyCombat || ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                if (!setTargetPointer.equals(SetTargetPointer.NONE)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setValue("damage", event.getAmount());
                        switch (setTargetPointer) {
                            case PLAYER:
                                effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                                break;
                            case PERMANENT:
                                effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                                break;
                        }

                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " deals " + (onlyCombat ? "combat " : "") + "damage to a player, " + super.getRule();
    }

}
