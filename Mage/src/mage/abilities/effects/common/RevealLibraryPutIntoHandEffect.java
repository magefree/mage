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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX
 */
public class RevealLibraryPutIntoHandEffect extends OneShotEffect {

    private DynamicValue amountCards;
    private FilterCard filter;
    private boolean anyOrder;

    public RevealLibraryPutIntoHandEffect(int amountCards, FilterCard filter, boolean anyOrder) {
        this(new StaticValue(amountCards), filter, anyOrder);
    }

    public RevealLibraryPutIntoHandEffect(DynamicValue amountCards, FilterCard filter, boolean anyOrder) {
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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, amountCards.calculate(game, source, this)));
        controller.revealCards(sourceObject.getIdName(), cards, game);

        Set<Card> cardsList = cards.getCards(game);
        Cards cardsToHand = new CardsImpl();
        for (Card card : cardsList) {
            if (filter.match(card, game)) {
                cardsToHand.add(card);
                cards.remove(card);
            }
        }
        controller.moveCards(cardsToHand, null, Zone.HAND, source, game);
        controller.putCardsOnBottomOfLibrary(cards, game, source, anyOrder);
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Reveal the top ");
        sb.append(CardUtil.numberToText(amountCards.toString())).append(" cards of your library. Put all ");
        sb.append(filter.getMessage());
        sb.append(" revealed this way into your hand and the rest on the bottom of your library");
        if (anyOrder) {
            sb.append(" in any order");
        }
        return sb.toString();
    }
}
