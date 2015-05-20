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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class FalseCure extends CardImpl {

    public FalseCure(UUID ownerId) {
        super(ownerId, 146, "False Cure", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "ONS";


        // Until end of turn, whenever a player gains life, that player loses 2 life for each 1 life he or she gained.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new FalseCureTriggeredAbility()));
    }

    public FalseCure(final FalseCure card) {
        super(card);
    }

    @Override
    public FalseCure copy() {
        return new FalseCure(this);
    }
}

class FalseCureTriggeredAbility extends DelayedTriggeredAbility {
    
    FalseCureTriggeredAbility() {
        super(new LoseLifeTargetEffect(0), Duration.EndOfTurn, false);
    }
    
    FalseCureTriggeredAbility(final FalseCureTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public FalseCureTriggeredAbility copy() {
        return new FalseCureTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.GAINED_LIFE) {
            this.getEffects().clear();
            Effect effect = new LoseLifeTargetEffect(2 * event.getAmount());
            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            this.addEffect(effect);
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Until end of turn, whenever a player gains life, that player loses 2 life for each 1 life he or she gained.";
    }
}
