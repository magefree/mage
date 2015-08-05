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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
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
 * @author emerald000
 */
public class PulseOfTheGrid extends CardImpl {

    public PulseOfTheGrid(UUID ownerId) {
        super(ownerId, 29, "Pulse of the Grid", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "DST";

        // Draw two cards, then discard a card. Then if an opponent has more cards in hand than you, return Pulse of the Grid to its owner's hand.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 1));
        this.getSpellAbility().addEffect(new PulseOfTheGridReturnToHandEffect());
    }

    public PulseOfTheGrid(final PulseOfTheGrid card) {
        super(card);
    }

    @Override
    public PulseOfTheGrid copy() {
        return new PulseOfTheGrid(this);
    }
}

class PulseOfTheGridReturnToHandEffect extends OneShotEffect {

    PulseOfTheGridReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw two cards, then discard a card. Then if an opponent has more cards in hand than you, return {this} to its owner's hand";
    }

    PulseOfTheGridReturnToHandEffect(final PulseOfTheGridReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheGridReturnToHandEffect copy() {
        return new PulseOfTheGridReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getHand().size() > controller.getHand().size()) {
                    Card card = game.getCard(source.getSourceId());
                    controller.moveCards(card, null, Zone.HAND, source, game);
                    return true;
                }
            }
        }
        return false;
    }
}
