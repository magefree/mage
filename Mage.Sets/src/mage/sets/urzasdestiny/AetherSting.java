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
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Backfir3
 */
public class AetherSting extends CardImpl<AetherSting> {

    public AetherSting(UUID ownerId) {
        super(ownerId, 76, "AEther Sting", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "UDS";
        this.color.setRed(true);

        // Whenever an opponent casts a creature spell, AEther Sting deals 1 damage to that player.
        this.addAbility(new AetherStingTriggeredAbility());
    }

    public AetherSting(final AetherSting card) {
        super(card);
    }

    @Override
    public AetherSting copy() {
        return new AetherSting(this);
    }
}

class AetherStingTriggeredAbility extends TriggeredAbilityImpl<AetherStingTriggeredAbility> {

    public AetherStingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
    }

    public AetherStingTriggeredAbility(final AetherStingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AetherStingTriggeredAbility copy() {
        return new AetherStingTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell, {this} deals 1 damage to that player.";
    }
}
