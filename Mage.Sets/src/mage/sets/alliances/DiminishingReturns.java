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
package mage.sets.alliances;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class DiminishingReturns extends CardImpl<DiminishingReturns> {

    public DiminishingReturns(UUID ownerId) {
        super(ownerId, 39, "Diminishing Returns", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "ALL";

        this.color.setBlue(true);

        // Each player shuffles his or her hand and graveyard into his or her library. You exile the top ten cards of your library. Then each player draws up to seven cards.
        this.getSpellAbility().addEffect(new DiminishingReturnsEffect());
    }

    public DiminishingReturns(final DiminishingReturns card) {
        super(card);
    }

    @Override
    public DiminishingReturns copy() {
        return new DiminishingReturns(this);
    }
}

class DiminishingReturnsEffect extends OneShotEffect<DiminishingReturnsEffect> {

    public DiminishingReturnsEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand and graveyard into his or her library. You exile the top ten cards of your library. Then each player draws up to seven cards.";
    }

    public DiminishingReturnsEffect(final DiminishingReturnsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            for (UUID playerId : sourcePlayer.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.getLibrary().addAll(player.getHand().getCards(game), game);
                    player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
                    player.shuffleLibrary(game);
                    player.getHand().clear();
                    player.getGraveyard().clear();
                }
            }

            for (int i = 0; i < 10; i++) {
                Card card = sourcePlayer.getLibrary().getFromTop(game);
                if (card != null) {
                    card.moveToExile(null, null, source.getSourceId(), game);
                }
            }

            for (UUID playerId : sourcePlayer.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsToDrawCount = player.getAmount(0, 7, "How many cards to draw (up to 7)?", game);
                    player.drawCards(cardsToDrawCount, game);
                }
            }
        }
        return true;
    }

    @Override
    public DiminishingReturnsEffect copy() {
        return new DiminishingReturnsEffect(this);
    }
}
