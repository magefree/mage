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
package mage.sets.judgment;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class CunningWish extends CardImpl<CunningWish> {

    public CunningWish(UUID ownerId) {
        super(ownerId, 37, "Cunning Wish", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "JUD";

        this.color.setBlue(true);

        // You may choose an instant card you own from outside the game, reveal that card, and put it into your hand. Exile Cunning Wish.
        this.getSpellAbility().addEffect(new CunningWishEffect());
    }

    public CunningWish(final CunningWish card) {
        super(card);
    }

    @Override
    public CunningWish copy() {
        return new CunningWish(this);
    }
}

class CunningWishEffect extends OneShotEffect<CunningWishEffect> {

    private static final String choiceText = "Choose an instant card you own from outside the game, and put it into your hand";

    private static final FilterCard filter = new FilterCard("instant card");
    static{
         filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public CunningWishEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "You may choose an instant card you own from outside the game, reveal that card, and put it into your hand. Exile Cunning Wish";
    }

    public CunningWishEffect(final CunningWishEffect effect) {
        super(effect);
    }

    @Override
    public CunningWishEffect copy() {
        return new CunningWishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            while (player.chooseUse(Constants.Outcome.Benefit, choiceText, game)) {
                Cards cards = player.getSideboard();
                if(cards.isEmpty()) {
                    game.informPlayer(player, "You have no cards outside the game.");
                    break;
                }

                Set<Card> filtered = cards.getCards(filter, game);
                if (filtered.isEmpty()) {
                    game.informPlayer(player, "You have no " + filter.getMessage() + " outside the game.");
                    break;
                }

                Cards filteredCards = new CardsImpl();
                for (Card card : filtered) {
                    filteredCards.add(card.getId());
                }

                TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                if (player.choose(Constants.Outcome.Benefit, filteredCards, target, game)) {
                    Card card = player.getSideboard().get(target.getFirstTarget(), game);
                    if (card != null) {

                        card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                        Cards revealCard = new CardsImpl();
                        revealCard.add(card);
                        player.revealCards("Cunning Wish", revealCard, game);
                        break;
                    }
                }
            }
            Card cardToExile = game.getCard(source.getSourceId());
            if(cardToExile != null)
            {
                cardToExile.moveToExile(null, "", source.getId(), game);
            }
        }
        return true;
    }

}