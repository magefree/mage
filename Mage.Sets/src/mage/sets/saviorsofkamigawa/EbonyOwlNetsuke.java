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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class EbonyOwlNetsuke extends CardImpl {

    public EbonyOwlNetsuke(UUID ownerId) {
        super(ownerId, 154, "Ebony Owl Netsuke", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "SOK";

        // At the beginning of each opponent's upkeep, if that player has seven or more cards in hand, Ebony Owl Netsuke deals 4 damage to him or her.
        this.addAbility(new EbonyOwlNetsukeTriggeredAbility(new DamageTargetEffect(4, true)));
    }


    public EbonyOwlNetsuke(final EbonyOwlNetsuke card) {
        super(card);
    }

    @Override
    public EbonyOwlNetsuke copy() {
        return new EbonyOwlNetsuke(this);
    }
}

class EbonyOwlNetsukeTriggeredAbility extends TriggeredAbilityImpl {
    
    EbonyOwlNetsukeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }
    
    EbonyOwlNetsukeTriggeredAbility(final EbonyOwlNetsukeTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public EbonyOwlNetsukeTriggeredAbility copy() {
        return new EbonyOwlNetsukeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null) {
                for (Effect effect: getEffects() ) {
                    effect.setTargetPointer(new FixedTarget(player.getId()));
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.getHand().size() >= 7;
    }   
    
    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has seven or more cards in hand, {this} deals 4 damage to him or her.";
    }
}
