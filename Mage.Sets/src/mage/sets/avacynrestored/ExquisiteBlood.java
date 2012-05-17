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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author noxx
 */
public class ExquisiteBlood extends CardImpl<ExquisiteBlood> {

    public ExquisiteBlood(UUID ownerId) {
        super(ownerId, 102, "Exquisite Blood", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // Whenever an opponent loses life, you gain that much life.
        ExquisiteBloodTriggeredAbility ability = new ExquisiteBloodTriggeredAbility();
        this.addAbility(ability);
    }

    public ExquisiteBlood(final ExquisiteBlood card) {
        super(card);
    }

    @Override
    public ExquisiteBlood copy() {
        return new ExquisiteBlood(this);
    }
}

class ExquisiteBloodTriggeredAbility extends TriggeredAbilityImpl<ExquisiteBloodTriggeredAbility> {

    public ExquisiteBloodTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, null);
    }

    public ExquisiteBloodTriggeredAbility(final ExquisiteBloodTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExquisiteBloodTriggeredAbility copy() {
        return new ExquisiteBloodTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_LIFE && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().clear();
            this.addEffect(new GainLifeEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent loses life, you gain that much life.";
    }
}
