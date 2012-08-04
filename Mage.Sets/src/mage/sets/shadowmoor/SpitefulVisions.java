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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class SpitefulVisions extends CardImpl<SpitefulVisions> {

    public SpitefulVisions(UUID ownerId) {
        super(ownerId, 198, "Spiteful Visions", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B/R}{B/R}");
        this.expansionSetCode = "SHM";

        this.color.setRed(true);
        this.color.setBlack(true);

        // At the beginning of each player's draw step, that player draws an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1), Constants.TargetController.ANY, false));

        // Whenever a player draws a card, Spiteful Visions deals 1 damage to that player.
        TriggeredAbility triggeredAbility = new SpitefulVisionsTriggeredAbility(new DamageTargetEffect(1), false);
        this.addAbility(triggeredAbility);
    }

    public SpitefulVisions(final SpitefulVisions card) {
        super(card);
    }

    @Override
    public SpitefulVisions copy() {
        return new SpitefulVisions(this);
    }
}

class SpitefulVisionsTriggeredAbility  extends TriggeredAbilityImpl<SpitefulVisionsTriggeredAbility> {

    public SpitefulVisionsTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public SpitefulVisionsTriggeredAbility(final SpitefulVisionsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && event.getPlayerId() != null) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player draws a card, Spiteful Visions deals 1 damage to that player.";
    }

    @Override
    public SpitefulVisionsTriggeredAbility copy() {
        return new SpitefulVisionsTriggeredAbility(this);
    }
}