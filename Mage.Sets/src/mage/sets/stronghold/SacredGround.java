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
package mage.sets.stronghold;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public class SacredGround extends CardImpl {

    public SacredGround(UUID ownerId) {
        super(ownerId, 112, "Sacred Ground", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "STH";


        // Whenever a spell or ability an opponent controls causes a land to be put into your graveyard from the battlefield, return that card to the battlefield.
        this.addAbility(new SacredGroundTriggeredAbility());
    }

    public SacredGround(final SacredGround card) {
        super(card);
    }

    @Override
    public SacredGround copy() {
        return new SacredGround(this);
    }
}

class SacredGroundTriggeredAbility extends TriggeredAbilityImpl {

    SacredGroundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToBattlefieldUnderYourControlTargetEffect());
    }

    SacredGroundTriggeredAbility(final SacredGroundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SacredGroundTriggeredAbility copy() {
        return new SacredGroundTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (GameEvent.EventType.ZONE_CHANGE.equals(event.getType()) &&
                game.getOpponents(this.getControllerId()).contains(game.getControllerId(event.getSourceId()))) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            if (Zone.BATTLEFIELD.equals(zce.getFromZone()) && Zone.GRAVEYARD.equals(zce.getToZone())) {
                Permanent targetPermanent = zce.getTarget();
                if (targetPermanent.getCardType().contains(CardType.LAND) && targetPermanent.getControllerId().equals(getControllerId())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(targetPermanent.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes a land to be put into your graveyard from the battlefield, " + super.getRule();
    }
}