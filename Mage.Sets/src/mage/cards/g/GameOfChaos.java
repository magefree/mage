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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class GameOfChaos extends CardImpl {

    public GameOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{R}{R}");

        // Flip a coin. 
        // If you win the flip, you gain 1 life and target opponent loses 1 life, and you decide whether to flip again. 
        // If you lose the flip, you lose 1 life and that opponent gains 1 life, and that player decides whether to flip again. 
        // Double the life stakes with each flip.
        this.getSpellAbility().addEffect(new GameOfChaosEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public GameOfChaos(final GameOfChaos card) {
        super(card);
    }

    @Override
    public GameOfChaos copy() {
        return new GameOfChaos(this);
    }
}

class GameOfChaosEffect extends OneShotEffect {
    
    public GameOfChaosEffect() {
        super(Outcome.Detriment);
        this.staticText = "Flip a coin. If you win the flip, you gain 1 life and target opponent loses 1 life, and you decide whether to flip again. If you lose the flip, you lose 1 life and that opponent gains 1 life, and that player decides whether to flip again. Double the life stakes with each flip.";
    }
    
    public GameOfChaosEffect(final GameOfChaosEffect effect) {
        super(effect);
    }
    
    @Override
    public GameOfChaosEffect copy() {
        return new GameOfChaosEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (you != null && targetOpponent != null) {
  
            boolean continueFlipping = true;
            boolean youWinFlip = you.flipCoin(game); // controller flips first
            boolean controllerWonLast = false;
            int lifeAmount = 1; // starts with 1 life
                
            while (continueFlipping) {
                
                if (youWinFlip) { // flipper of coin wins, flipper gain 1 and non-flipper loses 1
                    you.gainLife(lifeAmount, game);
                    targetOpponent.loseLife(lifeAmount, game, false);
                    if (targetOpponent.canRespond() && you.canRespond()) {
                        continueFlipping = you.chooseUse(outcome, "Flip again?", source, game);
                        controllerWonLast = true;
                    }
                } else { // non-flipper wins, flipper lose 1 and non-flipper gains 1
                    you.loseLife(lifeAmount, game, false);
                    targetOpponent.gainLife(lifeAmount, game);
                    if (targetOpponent.canRespond() && you.canRespond()) {
                        continueFlipping = targetOpponent.chooseUse(outcome, "Flip again?", source, game);
                        controllerWonLast = false;
                    }
                }
                
                if (!targetOpponent.canRespond() && !you.canRespond()) {
                    continueFlipping = false;
                }
                
                if (continueFlipping) {                    
                    lifeAmount *= 2; // double the life each time
                    if (controllerWonLast) {
                        youWinFlip = you.flipCoin(game);
                    } else {
                        youWinFlip = !targetOpponent.flipCoin(game); // negate the results for proper evaluation above
                    }
                }
            }

            return true;
        }
        return false;
    }
}