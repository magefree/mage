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
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DrawDiscardOneOfThemEffect extends OneShotEffect {

    private final int cardsToDraw;

    public DrawDiscardOneOfThemEffect(int cardsToDraw) {
        super(Outcome.DrawCard);
        this.cardsToDraw = cardsToDraw;
        staticText = "draw "
                + (cardsToDraw == 1 ? "a" : CardUtil.numberToText(cardsToDraw))
                + " card" + (cardsToDraw == 1 ? "" : "s")
                + ", then discard one of them";
    }

    public DrawDiscardOneOfThemEffect(final DrawDiscardOneOfThemEffect effect) {
        super(effect);
        this.cardsToDraw = effect.cardsToDraw;
    }

    @Override
    public DrawDiscardOneOfThemEffect copy() {
        return new DrawDiscardOneOfThemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards initialHand = controller.getHand().copy();
            controller.drawCards(cardsToDraw, game);
            Cards drawnCards = new CardsImpl(controller.getHand().copy());
            drawnCards.removeAll(initialHand);
            if (!drawnCards.isEmpty()) {
                TargetCard cardToDiscard = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                cardToDiscard.setNotTarget(true);
                if (controller.choose(Outcome.Discard, drawnCards, cardToDiscard, game)) {
                    Card card = controller.getHand().get(cardToDiscard.getFirstTarget(), game);
                    if (card != null) {
                        return controller.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
