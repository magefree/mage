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
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class ManifoldInsights extends CardImpl {

    public ManifoldInsights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Reveal the top ten cards of your library. Starting with the next opponent in turn order, each opponent chooses a different nonland card from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ManifoldInsightsEffect());
    }

    public ManifoldInsights(final ManifoldInsights card) {
        super(card);
    }

    @Override
    public ManifoldInsights copy() {
        return new ManifoldInsights(this);
    }
}

class ManifoldInsightsEffect extends OneShotEffect {

    public ManifoldInsightsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top ten cards of your library. Starting with the next opponent in turn order, each opponent chooses a different nonland card from among them. Put the chosen cards into your hand and the rest on the bottom of your library in a random order";
    }

    public ManifoldInsightsEffect(final ManifoldInsightsEffect effect) {
        super(effect);
    }

    @Override
    public ManifoldInsightsEffect copy() {
        return new ManifoldInsightsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards topLib = new CardsImpl();
            topLib.addAll(controller.getLibrary().getTopCards(game, 10));
            controller.revealCards(sourceObject.getIdName(), topLib, game);
            Cards chosenCards = new CardsImpl();
            if (game.getOpponents(controller.getId()).size() >= topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).size()) {
                chosenCards.addAll(topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game));
                topLib.removeAll(chosenCards);
            } else if (!topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty()) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (controller.hasOpponent(playerId, game)) {
                        Player opponent = game.getPlayer(playerId);
                        if (opponent != null && !topLib.getCards(StaticFilters.FILTER_CARD_NON_LAND, game).isEmpty()) {
                            TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD_NON_LAND);
                            if (opponent.choose(Outcome.Detriment, topLib, target, game)) {
                                Card card = game.getCard(target.getFirstTarget());
                                if (card != null) {
                                    topLib.remove(card);
                                    chosenCards.add(card);
                                }
                            }
                        }
                    }
                }
            }
            controller.moveCards(chosenCards, Zone.HAND, source, game);
            while (!topLib.isEmpty() && controller.isInGame()) {
                Card card = topLib.getRandom(game);
                if (card != null) {
                    topLib.remove(card);
                    controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
