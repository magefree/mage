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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public class PainMagnification extends CardImpl {

    public PainMagnification(UUID ownerId) {
        super(ownerId, 121, "Pain Magnification", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");
        this.expansionSetCode = "DIS";

        // Whenever an opponent is dealt 3 or more damage by a single source, that player discards a card.
        this.addAbility(new PainMagnificationTriggeredAbility());
    }

    public PainMagnification(final PainMagnification card) {
        super(card);
    }

    @Override
    public PainMagnification copy() {
        return new PainMagnification(this);
    }
}

class PainMagnificationTriggeredAbility extends TriggeredAbilityImpl {
    
    public PainMagnificationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1), false);
    }
    
    public PainMagnificationTriggeredAbility(final PainMagnificationTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PainMagnificationTriggeredAbility copy() {
        return new PainMagnificationTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            // If the damaged player is an opponent
            if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                int amount = event.getAmount();
                if(amount >= 3) {
                    // If at least 3 damage is dealt, set the the opponent as the Discard target
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }                
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent is dealt 3 or more damage by a single source, that player discards a card.";
    }
}
