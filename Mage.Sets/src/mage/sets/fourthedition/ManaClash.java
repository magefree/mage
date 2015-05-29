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
package mage.sets.fourthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class ManaClash extends CardImpl {

    public ManaClash(UUID ownerId) {
        super(ownerId, 228, "Mana Clash", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "4ED";

        // You and target opponent each flip a coin. Mana Clash deals 1 damage to each player whose coin comes up tails. Repeat this process until both players' coins come up heads on the same flip.
        this.getSpellAbility().addEffect(new ManaClashEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public ManaClash(final ManaClash card) {
        super(card);
    }

    @Override
    public ManaClash copy() {
        return new ManaClash(this);
    }
}

class ManaClashEffect extends OneShotEffect {
    
    public ManaClashEffect() {
        super(Outcome.Detriment);
        this.staticText = "You and target opponent each flip a coin. {this} deals 1 damage to each player whose coin comes up tails. Repeat this process until both players' coins come up heads on the same flip";
    }
    
    public ManaClashEffect(final ManaClashEffect effect) {
        super(effect);
    }
    
    @Override
    public ManaClashEffect copy() {
        return new ManaClashEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            boolean bothHeads = false;
            while (!bothHeads) {
                if (!targetOpponent.isInGame() || !controller.isInGame()) {
                    return false;
                }
                boolean controllerFlip = controller.flipCoin(game);
                boolean opponentFlip = targetOpponent.flipCoin(game);
                if (controllerFlip && opponentFlip) {
                    bothHeads = true;
                }
                if (!controllerFlip) {
                    controller.damage(1, source.getSourceId(), game, false, true);
                }
                if (!opponentFlip) {
                    targetOpponent.damage(1, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
