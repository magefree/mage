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
package mage.cards.f;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class FiremindsForesight extends CardImpl {

    public FiremindsForesight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{U}{R}");


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

class FiremindsForesightSearchEffect extends  OneShotEffect {

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
        Cards cardsInLibrary = new CardsImpl();
        cardsInLibrary.addAll(player.getLibrary().getCards(game));

        for (int cmc=3; cmc > 0; cmc--) {
            FilterCard filter = new FilterCard("instant card with converted mana cost " + cmc);
            filter.add(new CardTypePredicate(CardType.INSTANT));
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));


            cardsCount = cardsInLibrary.count(filter, game);
            if (cardsCount > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                if (player.searchLibrary(target, game)) {
                    for (UUID cardId: target.getTargets()) {
                        Card card = player.getLibrary().remove(cardId, game);
                        if (card != null){
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                            game.informPlayers(sourceCard.getName()+": " + player.getLogName() + " chose " + card.getName() );
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

        player.shuffleLibrary(source, game);
        return true;
    }
}