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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class HornOfGreed extends CardImpl<HornOfGreed> {

    public HornOfGreed(UUID ownerId) {
        super(ownerId, 129, "Horn of Greed", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "STH";

        // Whenever a player plays a land, that player draws a card.
        this.addAbility(new HornOfGreedAbility());
    }

    public HornOfGreed(final HornOfGreed card) {
        super(card);
    }

    @Override
    public HornOfGreed copy() {
        return new HornOfGreed(this);
    }
}

class HornOfGreedAbility extends TriggeredAbilityImpl<HornOfGreedAbility> {

    public HornOfGreedAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), false);
    }

    public HornOfGreedAbility(final HornOfGreedAbility ability) {
        super(ability);
    }

    @Override
    public HornOfGreedAbility copy() {
        return new HornOfGreedAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LAND_PLAYED) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player plays a land, " + super.getRule();
    }
}
