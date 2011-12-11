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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class UnderworldDreams extends CardImpl<UnderworldDreams> {

    public UnderworldDreams(UUID ownerId) {
        super(ownerId, 115, "Underworld Dreams", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");
        this.expansionSetCode = "M10";

        this.color.setBlack(true);

        // Whenever an opponent draws a card, Underworld Dreams deals 1 damage to him or her.
        this.addAbility(new UnderworldDreamsTriggeredAbility());
    }

    public UnderworldDreams(final UnderworldDreams card) {
        super(card);
    }

    @Override
    public UnderworldDreams copy() {
        return new UnderworldDreams(this);
    }
}

class UnderworldDreamsTriggeredAbility extends TriggeredAbilityImpl<UnderworldDreamsTriggeredAbility> {

    UnderworldDreamsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), false);
    }

    UnderworldDreamsTriggeredAbility(final UnderworldDreamsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnderworldDreamsTriggeredAbility copy() {
        return new UnderworldDreamsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card, Underworld Dreams deals 1 damage to him or her";
    }
}
