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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class ReforgeTheSoul extends CardImpl<ReforgeTheSoul> {

    public ReforgeTheSoul(UUID ownerId) {
        super(ownerId, 151, "Reforge the Soul", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "AVR";

        this.color.setRed(true);

        // Each player discards his or her hand and draws seven cards.
        this.getSpellAbility().addEffect(new ReforgeTheSoulEffect());

        this.addAbility(new MiracleAbility(new ManaCostsImpl("{1}{R}")));
    }

    public ReforgeTheSoul(final ReforgeTheSoul card) {
        super(card);
    }

    @Override
    public ReforgeTheSoul copy() {
        return new ReforgeTheSoul(this);
    }
}

class ReforgeTheSoulEffect extends OneShotEffect<ReforgeTheSoulEffect> {

    public ReforgeTheSoulEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player discards his or her hand and draws seven cards";
    }

    public ReforgeTheSoulEffect(final ReforgeTheSoulEffect effect) {
        super(effect);
    }

    @Override
    public ReforgeTheSoulEffect copy() {
        return new ReforgeTheSoulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card : player.getHand().getCards(game)) {
                        player.discard(card, source, game);
                    }

                    player.drawCards(7, game);
                }
            }
            return true;
        }
        return false;
    }
}
