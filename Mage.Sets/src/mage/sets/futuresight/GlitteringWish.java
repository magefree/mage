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
package mage.sets.futuresight;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Plopman
 */
public class GlitteringWish extends CardImpl<GlitteringWish> {

    public GlitteringWish(UUID ownerId) {
        super(ownerId, 156, "Glittering Wish", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{G}{W}");
        this.expansionSetCode = "FUT";

        this.color.setGreen(true);
        this.color.setWhite(true);

        // You may choose a multicolored card you own from outside the game, reveal that card, and put it into your hand. Exile Glittering Wish.
        this.getSpellAbility().addEffect(new GlitteringWishEffect());
    }

    public GlitteringWish(final GlitteringWish card) {
        super(card);
    }

    @Override
    public GlitteringWish copy() {
        return new GlitteringWish(this);
    }
}

class GlitteringWishEffect extends OneShotEffect<GlitteringWishEffect> {

    private static final String choiceText = "Choose a multicolored card you own from outside the game, and put it into your hand";

    private static final FilterCard filter = new FilterCard("multicolored card");
    static{
         filter.add(new Predicate<MageObject>() {

            @Override
            public boolean apply(MageObject input, Game game) {
                return input.getColor().isMulticolored();
            }

            @Override
            public String toString() {
                return "Multicolored";
            }
        });
    }

    public GlitteringWishEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "You may choose a multicolored card you own from outside the game, reveal that card, and put it into your hand. Exile Glittering Wish";
    }

    public GlitteringWishEffect(final GlitteringWishEffect effect) {
        super(effect);
    }

    @Override
    public GlitteringWishEffect copy() {
        return new GlitteringWishEffect(this);
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
                        player.revealCards("Glittering Wish", revealCard, game);
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