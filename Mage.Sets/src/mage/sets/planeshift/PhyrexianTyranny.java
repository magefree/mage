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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class PhyrexianTyranny extends CardImpl {

    public PhyrexianTyranny(UUID ownerId) {
        super(ownerId, 118, "Phyrexian Tyranny", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");
        this.expansionSetCode = "PLS";


        // Whenever a player draws a card, that player loses 2 life unless he or she pays {2}.
        this.addAbility(new PhyrexianTyrannyTriggeredAbility());
    }

    public PhyrexianTyranny(final PhyrexianTyranny card) {
        super(card);
    }

    @Override
    public PhyrexianTyranny copy() {
        return new PhyrexianTyranny(this);
    }
}

class PhyrexianTyrannyTriggeredAbility extends TriggeredAbilityImpl {
    
    PhyrexianTyrannyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PhyrexianTyrannyEffect(), false);
    }
    
    PhyrexianTyrannyTriggeredAbility(final PhyrexianTyrannyTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PhyrexianTyrannyTriggeredAbility copy() {
        return new PhyrexianTyrannyTriggeredAbility(this);
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof PhyrexianTyrannyEffect) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a player draws a card, that player loses 2 life unless he or she pays {2}";
    }
}

class PhyrexianTyrannyEffect extends OneShotEffect {

    PhyrexianTyrannyEffect() {
        super(Outcome.Neutral);
        this.staticText = "that player loses 2 life unless he or she pays {2}";
    }

    PhyrexianTyrannyEffect(final PhyrexianTyrannyEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianTyrannyEffect copy() {
        return new PhyrexianTyrannyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Cost cost = new GenericManaCost(2);
            if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
                player.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
