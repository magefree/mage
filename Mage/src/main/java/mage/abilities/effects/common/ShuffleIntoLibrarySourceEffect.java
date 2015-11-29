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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */

public class ShuffleIntoLibrarySourceEffect extends OneShotEffect {
    
    public ShuffleIntoLibrarySourceEffect() {
        super(Outcome.Neutral);
        staticText = "shuffle it into its owner's library";
    }

    public ShuffleIntoLibrarySourceEffect(final ShuffleIntoLibrarySourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = source.getSourceObjectIfItStillExists(game);
        if (mageObject != null) {
            Zone fromZone = game.getState().getZone(mageObject.getId());
            Player owner;
            if (mageObject instanceof Permanent) {
                owner = game.getPlayer(((Permanent) mageObject).getOwnerId());
                if (owner != null) {
                    owner.moveCardToLibraryWithInfo((Permanent)mageObject, source.getSourceId(), game, fromZone, true, true);                
                    owner.shuffleLibrary(game);
                    return true;
                }
            } else if (mageObject instanceof Card) {
                owner = game.getPlayer(((Card) mageObject).getOwnerId());
                if (owner != null) {
                    owner.moveCardToLibraryWithInfo((Card)mageObject, source.getSourceId(), game, fromZone, true, true);                
                    owner.shuffleLibrary(game);
                    return true;
                }
            }

            
        }
        return false;
    }

    @Override
    public ShuffleIntoLibrarySourceEffect copy() {
        return new ShuffleIntoLibrarySourceEffect(this);
    }
}
