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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
*
* @author Xavierv3131
*/
public class CoastalPiracy extends CardImpl {

    public CoastalPiracy(UUID ownerId) {
        super(ownerId, 68, "Coastal Piracy", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "MMQ";


        // Whenever a creature you control deals combat damage to an opponent, you may draw a card.
        this.addAbility(new CoastalPiracyTriggeredAbility());
    }

    public CoastalPiracy(final CoastalPiracy card) {
        super(card);
    }

    @Override
    public CoastalPiracy copy() {
        return new CoastalPiracy(this);
    }
}

class CoastalPiracyTriggeredAbility extends TriggeredAbilityImpl {

    public CoastalPiracyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.optional = true;
    }

    public CoastalPiracyTriggeredAbility(final CoastalPiracyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CoastalPiracyTriggeredAbility copy() {
        return new CoastalPiracyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) &&
        	game.getOpponents(this.controllerId).contains(((DamagedPlayerEvent) event).getPlayerId())) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.getControllerId().equals(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage to an opponent, you may draw a card.";
    }

}