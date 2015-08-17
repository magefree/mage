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
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class FatesealEffect extends OneShotEffect {

    protected static FilterCard filter1 = new FilterCard("card to put on the bottom of opponent's library");

    protected int fatesealNumber;

    public FatesealEffect(int fatesealNumber) {
        super(Outcome.Benefit);
        this.fatesealNumber = fatesealNumber;
        this.setText();
    }

    public FatesealEffect(final FatesealEffect effect) {
        super(effect);
        this.fatesealNumber = effect.fatesealNumber;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            if (controller.choose(outcome, target, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent == null) {
                    return false;
                }
                boolean revealed = opponent.isTopCardRevealed(); // by looking at the cards with fateseal you have not to reveal the next card
                opponent.setTopCardRevealed(false);
                Cards cards = new CardsImpl();
                int count = Math.min(fatesealNumber, opponent.getLibrary().size());
                if (count == 0) {
                    return true;
                }
                for (int i = 0; i < count; i++) {
                    Card card = opponent.getLibrary().removeFromTop(game);
                    cards.add(card);
                }
                TargetCard target1 = new TargetCard(Zone.LIBRARY, filter1);
                target1.setRequired(false);
                // move cards to the bottom of the library
                while (cards.size() > 0 && controller.choose(Outcome.Detriment, cards, target1, game)) {
                    if (!controller.canRespond() || !opponent.canRespond()) {
                        return false;
                    }
                    Card card = cards.get(target1.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                    }
                    target1.clearChosen();
                }
                // move cards to the top of the library
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
                game.fireEvent(new GameEvent(GameEvent.EventType.FATESEAL, opponent.getId(), source.getSourceId(), source.getControllerId()));
                controller.setTopCardRevealed(revealed);
                return true;
            }

        }
        return false;
    }

    @Override
    public FatesealEffect copy() {
        return new FatesealEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("fateseal ").append(fatesealNumber);
        if (fatesealNumber == 1) {
            sb.append(". <i>(To fateseal 1, its controller looks at the top card of an opponent's library, then he or she may put that card on the bottom of that library.)</i>");
        } else {
            sb.append(". <i>(To fateseal ");
            sb.append(CardUtil.numberToText(fatesealNumber));
            sb.append(", look at the top two cards of an opponent's library, then put any number of them on the bottom of that player's library and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
