/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawDiscardControllerEffect extends OneShotEffect<DrawDiscardControllerEffect> {

    private int cardsToDraw;
    private int cardsToDiscard;

    public DrawDiscardControllerEffect() {
        this(1,1);
    }

    public DrawDiscardControllerEffect(int cardsToDraw, int cardsToDiscard) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        this.cardsToDiscard = cardsToDiscard;
        staticText = new StringBuilder("Draw ")
                .append(cardsToDraw == 1?"a": CardUtil.numberToText(cardsToDraw))
                .append(" card").append(cardsToDraw == 1?" ": "s")
                .append(", then discard ")
                .append(cardsToDiscard == 1?"a": CardUtil.numberToText(cardsToDiscard))
                .append(" card").append(cardsToDiscard == 1?" ": "s").toString();
    }

    public DrawDiscardControllerEffect(final DrawDiscardControllerEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
        this.cardsToDiscard = effect.cardsToDiscard;
    }

    @Override
    public DrawDiscardControllerEffect copy() {
        return new DrawDiscardControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(cardsToDraw, game);
            player.discard(cardsToDiscard, source, game);
            return true;
        }
        return false;
    }

}
