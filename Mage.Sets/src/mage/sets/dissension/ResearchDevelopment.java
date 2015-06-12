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
package mage.sets.dissension;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.ElementalToken;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author magenoxx
 */
public class ResearchDevelopment extends SplitCard {

    public ResearchDevelopment(UUID ownerId) {
        super(ownerId, 155, "Research", "Development", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{G}{U}", "{3}{U}{R}", false);
        this.expansionSetCode = "DIS";

        // Choose up to four cards you own from outside the game and shuffle them into your library.
        getLeftHalfCard().getSpellAbility().addEffect(new ResearchEffect());

        // Put a 3/1 red Elemental creature token onto the battlefield unless any opponent has you draw a card. Repeat this process two more times.
        getRightHalfCard().getSpellAbility().addEffect(new DevelopmentEffect());
    }

    public ResearchDevelopment(final ResearchDevelopment card) {
        super(card);
    }

    @Override
    public ResearchDevelopment copy() {
        return new ResearchDevelopment(this);
    }
}

class ResearchEffect extends OneShotEffect {

    private static final String choiceText = "Choose a card you own from outside the game";

    private static final FilterCard filter = new FilterCard("card");


    public ResearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose up to four cards you own from outside the game and shuffle them into your library";
    }

    public ResearchEffect(final ResearchEffect effect) {
        super(effect);
    }

    @Override
    public ResearchEffect copy() {
        return new ResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            StringBuilder textToAsk = new StringBuilder(choiceText);
            textToAsk.append(" (0)");
            int count = 0;
            while (player.chooseUse(Outcome.Benefit, textToAsk.toString(), game)) {
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

                TargetCard target = new TargetCard(Zone.PICK, filter);
                if (player.choose(Outcome.Benefit, filteredCards, target, game)) {
                    Card card = player.getSideboard().get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                        count++;
                        textToAsk = new StringBuilder(choiceText);
                        textToAsk.append(" (");
                        textToAsk.append(count);
                        textToAsk.append(")");
                    }
                }

                if (count == 4) {
                    break;
                }
            }

            game.informPlayers(player.getLogName() + " has chosen " + count + " card(s) to shuffle into his or her library.");

            if (count > 0) {
                player.shuffleLibrary(game);
            }

            return true;
        }

        return false;
    }
}

class DevelopmentEffect extends OneShotEffect {

    public DevelopmentEffect() {
        super(Outcome.Benefit);
        staticText = "Put a 3/1 red Elemental creature token onto the battlefield unless any opponent has you draw a card. Repeat this process two more times.";
    }

    DevelopmentEffect(final DevelopmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (int i = 0; i < 3; i++) {
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                boolean putToken = true;
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && opponent.chooseUse(Outcome.Detriment, 
                            "Allow " + player.getLogName() + " to draw a card instead? (" + Integer.toString(i+1) + ")", game)) {
                        game.informPlayers(opponent.getLogName() + " had chosen to let " + player.getLogName() + " draw a card.");
                        player.drawCards(1, game);
                        putToken = false;
                        break;
                    }
                }
                if (putToken) {
                    new CreateTokenEffect(new ElementalToken()).apply(game, source);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public DevelopmentEffect copy() {
        return new DevelopmentEffect(this);
    }

}