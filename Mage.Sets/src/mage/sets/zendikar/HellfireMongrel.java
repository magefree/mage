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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class HellfireMongrel extends CardImpl<HellfireMongrel> {

    public HellfireMongrel(UUID ownerId) {
        super(ownerId, 130, "Hellfire Mongrel", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Elemental");
        this.subtype.add("Hound");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Hellfire Mongrel deals 2 damage to him or her.
        this.addAbility(new HellfireMongrelTriggeredAbility());
    }

    public HellfireMongrel(final HellfireMongrel card) {
        super(card);
    }

    @Override
    public HellfireMongrel copy() {
        return new HellfireMongrel(this);
    }
}

class HellfireMongrelTriggeredAbility extends TriggeredAbilityImpl<HellfireMongrelTriggeredAbility> {

    public HellfireMongrelTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public HellfireMongrelTriggeredAbility(final HellfireMongrelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HellfireMongrelTriggeredAbility copy() {
        return new HellfireMongrelTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && player.getHand().size() < 3) {
                this.getEffects().clear();
                DamageTargetEffect effect = new DamageTargetEffect(2);
                effect.setTargetPointer(new FixedTarget(player.getId()));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, {this} deals 2 damage to him or her.";
    }
}
