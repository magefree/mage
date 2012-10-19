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
package mage.sets.returntoravnica;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class FiremindsForesight extends CardImpl<FiremindsForesight> {

    public FiremindsForesight(UUID ownerId) {
        super(ownerId, 162, "Firemind's Foresight", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{U}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);
        this.color.setRed(true);

        // Search your library for an instant card with converted mana cost 3, reveal it,
        // and put it into your hand. Then repeat this process for instant cards with
        // converted mana costs 2 and 1. Then shuffle your library.
        this.getSpellAbility().addEffect(new FiremindsForesightSearchEffect());
    }

    public FiremindsForesight(final FiremindsForesight card) {
        super(card);
    }

    @Override
    public FiremindsForesight copy() {
        return new FiremindsForesight(this);
    }
}

class FiremindsForesightSearchEffect extends  OneShotEffect<FiremindsForesightSearchEffect> {

    public FiremindsForesightSearchEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for an instant card with converted mana cost 3, reveal it, and put it into your hand. Then repeat this process for instant cards with converted mana costs 2 and 1. Then shuffle your library";
    }

    public FiremindsForesightSearchEffect(final FiremindsForesightSearchEffect effect) {
        super(effect);
    }

    @Override
    public FiremindsForesightSearchEffect copy() {
        return new FiremindsForesightSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        int cardsCount;
        Cards cardToReveal = new CardsImpl();
        Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
        cardsInLibrary.addAll(player.getLibrary().getCards(game));

        for (int cmc=3; cmc > 0; cmc--) {
            FilterCard filter = new FilterCard("instant card with converted mana cost " + cmc);
            filter.add(new CardTypePredicate(CardType.INSTANT));
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, cmc));


            cardsCount = cardsInLibrary.count(filter, game);
            if (cardsCount > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                if (player.searchLibrary(target, game)) {
                    for (UUID cardId: (List<UUID>)target.getTargets()) {
                        Card card = player.getLibrary().remove(cardId, game);
                        if (card != null){
                            card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                            game.informPlayers(sourceCard.getName()+": " + player.getName() + " chose " + card.getName() );
                            cardsInLibrary.remove(card);
                            cardToReveal.add(card);
                            player.revealCards(sourceCard.getName(), cardToReveal, game);
                        }
                    }
                }
            } else {
                player.lookAtCards(filter.getMessage(), cardsInLibrary, game);
            }
        }

        player.shuffleLibrary(game);
        return true;
    }
}