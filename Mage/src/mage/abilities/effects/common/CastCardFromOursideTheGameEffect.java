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

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Set;

/**
 * @author magenoxx_at_gmail.com
 */
public class CastCardFromOursideTheGameEffect extends OneShotEffect<CastCardFromOursideTheGameEffect> {

    private static final String choiceText = "Cast a card from outside the game?";

    private FilterCard filterCard;

    public CastCardFromOursideTheGameEffect(FilterCard filter, String ruleText) {
        super(Constants.Outcome.Benefit);
        this.staticText = ruleText;
        this.filterCard = filter;
    }

    public CastCardFromOursideTheGameEffect(final CastCardFromOursideTheGameEffect effect) {
        super(effect);
        filterCard = effect.filterCard;
    }

    @Override
    public CastCardFromOursideTheGameEffect copy() {
        return new CastCardFromOursideTheGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        while (player.chooseUse(Constants.Outcome.Benefit, choiceText, game)) {
            Cards cards = player.getSideboard();
            if (cards.isEmpty()) {
                game.informPlayer(player, "You have no cards outside the game.");
                return false;
            }

            Set<Card> filtered = cards.getCards(filterCard, game);
            if (filtered.isEmpty()) {
                game.informPlayer(player, "You have no " + filterCard.getMessage() + " outside the game.");
                return false;
            }
            
            Cards filteredCards = new CardsImpl();
            for (Card card : filtered) {
                filteredCards.add(card.getId());
            }

            TargetCard target = new TargetCard(Constants.Zone.PICK, filterCard);
            if (player.choose(Constants.Outcome.Benefit, filteredCards, target, game)) {
                Card card = player.getSideboard().get(target.getFirstTarget(), game);
                if (card != null) {
                    player.cast(card.getSpellAbility(), game, true);
                }
            }
        }

        return true;
    }

}