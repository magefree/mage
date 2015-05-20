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
package mage.sets.urzassaga;

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
 * @author emerald000
 */
public class Windfall extends CardImpl {

    public Windfall(UUID ownerId) {
        super(ownerId, 111, "Windfall", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "USG";


        // Each player discards his or her hand, then draws cards equal to the greatest number of cards a player discarded this way.
        this.getSpellAbility().addEffect(new WindfallEffect());
    }

    public Windfall(final Windfall card) {
        super(card);
    }

    @Override
    public Windfall copy() {
        return new Windfall(this);
    }
}

class WindfallEffect extends OneShotEffect {
    WindfallEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards his or her hand, then draws cards equal to the greatest number of cards a player discarded this way.";
    }

    WindfallEffect(final WindfallEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxDiscarded = 0;
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer == null) {
            return false;
        }
        for (UUID playerId : sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int discarded = 0;
                for (Card c : player.getHand().getCards(game)) {
                    if (player.discard(c, source, game)) {
                        discarded++;
                    }
                }
                if (discarded > maxDiscarded) {
                    maxDiscarded = discarded;
                }
            }
        }
        for (UUID playerId : sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(maxDiscarded, game);
            }
        }
        return true;
    }

    @Override
    public WindfallEffect copy() {
        return new WindfallEffect(this);
    }
}