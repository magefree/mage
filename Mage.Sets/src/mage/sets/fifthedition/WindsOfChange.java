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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class WindsOfChange extends CardImpl {

    public WindsOfChange(UUID ownerId) {
        super(ownerId, 275, "Winds of Change", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "5ED";

        this.color.setRed(true);

        // Each player shuffles the cards from his or her hand into his or her library, then draws that many cards.
        this.getSpellAbility().addEffect(new WindsOfChangeEffect());
    }

    public WindsOfChange(final WindsOfChange card) {
        super(card);
    }

    @Override
    public WindsOfChange copy() {
        return new WindsOfChange(this);
    }
}

class WindsOfChangeEffect extends OneShotEffect {

    public WindsOfChangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles the cards from his or her hand into his or her library, then draws that many cards";
    }

    public WindsOfChangeEffect(final WindsOfChangeEffect effect) {
        super(effect);
    }

    @Override
    public WindsOfChangeEffect copy() {
        return new WindsOfChangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int cardsHand = player.getHand().size();
                    if (cardsHand > 0){
                        for (Card card: player.getHand().getCards(game)) {
                            player.removeFromHand(card, game);
                            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                        }
                        game.informPlayers(player.getLogName() + " shuffles the cards from his or her hand into his or her library");
                        player.shuffleLibrary(game);
                        player.drawCards(cardsHand, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
