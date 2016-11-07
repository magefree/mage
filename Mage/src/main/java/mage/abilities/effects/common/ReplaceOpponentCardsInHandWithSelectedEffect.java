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
package mage.abilities.effects.common;

import static java.lang.Integer.min;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Eirkei
 */
public class ReplaceOpponentCardsInHandWithSelectedEffect extends OneShotEffect {

    public ReplaceOpponentCardsInHandWithSelectedEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent puts the cards from his or her hand on top of his or her library. Search that player's library for that many cards. The player puts those cards into his or her hand, then shuffles his or her library.";
    }

    public ReplaceOpponentCardsInHandWithSelectedEffect(final ReplaceOpponentCardsInHandWithSelectedEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        
        if (controller != null && targetOpponent != null){
            int originalHandSize = targetOpponent.getHand().size();
            
            if (originalHandSize > 0) {
                targetOpponent.putCardsOnTopOfLibrary(targetOpponent.getHand(), game, source, false);
                
                int librarySize = targetOpponent.getLibrary().size();
                int searchLibraryForNum = min(originalHandSize, librarySize);
                
                TargetCardInLibrary target = new TargetCardInLibrary(searchLibraryForNum, searchLibraryForNum, new FilterCard());
                
                controller.searchLibrary(target, game, targetOpponent.getId());
                
                for (UUID cardId : target.getTargets()) {
                    Card targetCard = game.getCard(cardId);
                    targetOpponent.moveCards(targetCard, Zone.HAND, source, game);
                }
                
                targetOpponent.shuffleLibrary(source, game);
            }
            
            return true;
        }
        
        return false;
    }

    @Override
    public ReplaceOpponentCardsInHandWithSelectedEffect copy() {
        return new ReplaceOpponentCardsInHandWithSelectedEffect(this);
    }
    
}