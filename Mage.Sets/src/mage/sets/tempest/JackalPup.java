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
package mage.sets.tempest;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth

 */
public class JackalPup extends CardImpl<JackalPup> {

    public JackalPup(UUID ownerId) {
        super(ownerId, 183, "Jackal Pup", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Hound");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Jackal Pup is dealt damage, it deals that much damage to you.
        this.addAbility(new JackalPupTriggeredAbility());
        
    }

    public JackalPup(final JackalPup card) {
        super(card);
    }

    @Override
    public JackalPup copy() {
        return new JackalPup(this);
    }
}

class JackalPupTriggeredAbility extends TriggeredAbilityImpl<JackalPupTriggeredAbility> {

    public JackalPupTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new JackalPupEffect());
    }

    public JackalPupTriggeredAbility(final JackalPupTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public JackalPupTriggeredAbility copy() {
        return new JackalPupTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
                        this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                        return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, " + super.getRule();
    }
}


class JackalPupEffect extends OneShotEffect<JackalPupEffect> {

        public JackalPupEffect() {
            super(Constants.Outcome.Damage);
            staticText = "it deals that much damage to you";
        }

    public JackalPupEffect(final JackalPupEffect effect) {
        super(effect);
    }

    @Override
    public JackalPupEffect copy() {
        return new JackalPupEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            you.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), game, false, true);
        }
        return true;
    }


}