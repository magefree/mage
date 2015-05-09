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
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class RevealAndShuffleIntoLibrarySourceEffect extends OneShotEffect {
    
    public RevealAndShuffleIntoLibrarySourceEffect() {
        super(Outcome.Neutral);
        staticText = "reveal {this} and shuffle it into its owner's library instead";
    }

    public RevealAndShuffleIntoLibrarySourceEffect(final RevealAndShuffleIntoLibrarySourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            Player owner = null;
            Cards cards = new CardsImpl();
            Permanent permanent = null;
            if (sourceObject instanceof Spell) {
                sourceObject = ((Spell)sourceObject).getCard();
            }
            if (sourceObject instanceof Permanent) {
                permanent = (Permanent) sourceObject;
                owner = game.getPlayer(permanent.getOwnerId());
                if (sourceObject instanceof PermanentCard) {
                    cards.add(permanent);
                }
            } else if (sourceObject instanceof Card) {
                owner = game.getPlayer(((Card)sourceObject).getOwnerId());
                cards.add((Card)sourceObject);
            }
            if (owner != null) {
                Zone fromZone = game.getState().getZone(sourceObject.getId());
                if (!cards.isEmpty()) {
                    controller.revealCards(sourceObject.getName(), cards, game);
                }
                if (permanent != null) {
                    controller.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, fromZone, true, true);
                } else {
                    controller.moveCardToLibraryWithInfo((Card)sourceObject, source.getSourceId(), game, fromZone, true, true);
                }
                if (!cards.isEmpty()) {
                    controller.shuffleLibrary(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RevealAndShuffleIntoLibrarySourceEffect copy() {
        return new RevealAndShuffleIntoLibrarySourceEffect(this);
    }

}
