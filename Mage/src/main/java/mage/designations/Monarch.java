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
package mage.designations;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class Monarch extends Designation {

    public Monarch() {
        super("The Monarch", "CN2");
        addAbility(new MonarchDrawTriggeredAbility());
        addAbility(new MonarchDealsCombatDamageToAPlayerTriggeredAbility());
    }

    /**
     *
     * @param game
     * @param controllerId
     */
    @Override
    public void start(Game game, UUID controllerId) {

    }

    @Override
    public boolean isAllCreatureTypes() {
        return false;
    }

    @Override
    public void setIsAllCreatureTypes(boolean value) {

    }
}

// At the beginning of the monarch’s end step, that player draws a card
class MonarchDrawTriggeredAbility extends BeginningOfEndStepTriggeredAbility {

    public MonarchDrawTriggeredAbility() {
        super(Zone.ALL, new DrawCardTargetEffect(1), TargetController.ANY, null, false);
    }

    public MonarchDrawTriggeredAbility(final MonarchDrawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getMonarchId() != null && event.getPlayerId().equals(game.getMonarchId())) {
            setControllerId(game.getMonarchId());
            getEffects().get(0).setTargetPointer(new FixedTarget(game.getMonarchId()));
            return true;
        }
        return false;
    }

    @Override
    public MonarchDrawTriggeredAbility copy() {
        return new MonarchDrawTriggeredAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of the monarch’s end step, that player draws a card.";
    }
}

// Whenever a creature deals combat damage to the monarch, its controller becomes the monarch.
class MonarchDealsCombatDamageToAPlayerTriggeredAbility extends TriggeredAbilityImpl {

    public MonarchDealsCombatDamageToAPlayerTriggeredAbility() {
        super(Zone.ALL, new BecomesMonarchTargetEffect(), false);
    }

    public MonarchDealsCombatDamageToAPlayerTriggeredAbility(final MonarchDealsCombatDamageToAPlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            MageObject damagingObject = game.getObject(event.getSourceId());
            if (damagingObject != null
                    && damagingObject instanceof Permanent
                    && damagingObject.isCreature()
                    && event.getTargetId().equals(game.getMonarchId())) {
                setControllerId(event.getPlayerId());
                getEffects().get(0).setTargetPointer(new FixedTarget(((Permanent) damagingObject).getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public MonarchDealsCombatDamageToAPlayerTriggeredAbility copy() {
        return new MonarchDealsCombatDamageToAPlayerTriggeredAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to the monarch, its controller becomes the monarch.";
    }


}
