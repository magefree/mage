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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class TimeSpiral extends CardImpl<TimeSpiral> {

    public TimeSpiral(UUID ownerId) {
        super(ownerId, 103, "Time Spiral", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");
        this.expansionSetCode = "USG";

        this.color.setBlue(true);

        // Exile Time Spiral. Each player shuffles his or her graveyard and hand into his or her library, then draws seven cards. You untap up to six lands.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
        this.getSpellAbility().addEffect(new TimeSpiralEffect());
        this.getSpellAbility().addEffect(new UntapLandsEffect(6));
    }
    public TimeSpiral(final TimeSpiral card) {
        super(card);
    }

    @Override
    public TimeSpiral copy() {
        return new TimeSpiral(this);
    }
}

class TimeSpiralEffect extends OneShotEffect<TimeSpiralEffect> {

    public TimeSpiralEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand and graveyard into his or her library, then draws seven cards";
    }

    public TimeSpiralEffect(final TimeSpiralEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        for (UUID playerId: sourcePlayer.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.getLibrary().addAll(player.getHand().getCards(game), game);
                player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
                player.shuffleLibrary(game);
                player.getHand().clear();
                player.getGraveyard().clear();
                player.drawCards(7, game);
            }
        }
        return true;
    }

    @Override
    public TimeSpiralEffect copy() {
        return new TimeSpiralEffect(this);
    }

}