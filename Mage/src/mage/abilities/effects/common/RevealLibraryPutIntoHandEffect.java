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

import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */

public class RevealLibraryPutIntoHandEffect extends OneShotEffect<RevealLibraryPutIntoHandEffect> {

    private int amountCards;
    private FilterCard filter;
    private boolean anyOrder;

    public RevealLibraryPutIntoHandEffect(int amountCards) {
        this(amountCards, new FilterCard("cards"), true);
    }

    public RevealLibraryPutIntoHandEffect(int amountCards, FilterCard filter, boolean anyOrder) {
        super(Outcome.DrawCard);
        this.amountCards = amountCards;
        this.filter = filter;
        this.anyOrder = anyOrder;
        this.staticText = setText();
    }

    public RevealLibraryPutIntoHandEffect(final RevealLibraryPutIntoHandEffect effect) {
        super(effect);
        this.amountCards = effect.amountCards;
        this.filter = effect.filter;
        this.anyOrder = effect.anyOrder;
    }

    @Override
    public RevealLibraryPutIntoHandEffect copy() {
        return new RevealLibraryPutIntoHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        int amount = Math.min(amountCards, player.getLibrary().size());
        for (int i = 0; i < amount; i++) {
            cards.add(player.getLibrary().removeFromTop(game));
        }
        player.revealCards(new StringBuilder("Put ").append(filter.getMessage()).append(" into hand").toString(), cards, game);

        Set<Card> cardsList = cards.getCards(game);
        for (Card card : cardsList) {
            if (filter.match(card, game)) {
                card.moveToZone(Zone.HAND, source.getId(), game, true);
                cards.remove(card);
            }
        }

        while (cards.size() > 1) {
            Card card;
            if (anyOrder) {
                TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
                target.setRequired(true);
                player.choose(Outcome.Neutral, cards, target, game);
                card = cards.get(target.getFirstTarget(), game);
            } else {
                card = cards.get(cards.iterator().next(),game);
            }
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
        }

        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Reveal the top ");
        sb.append(CardUtil.numberToText(amountCards)).append(" cards of your library. Put all ");
        sb.append(filter.getMessage());
        sb.append(" revealed this way into your hand and the rest on the bottom of your library");
        if (anyOrder) {
            sb.append(" in any order");
        }
        return sb.toString();
    }
}
