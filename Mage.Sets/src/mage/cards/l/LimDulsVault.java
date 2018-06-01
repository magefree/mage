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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class LimDulsVault extends CardImpl {

    public LimDulsVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Look at the top five cards of your library. As many times as you choose, you may pay 1 life, put those cards on the bottom of your library in any order, then look at the top five cards of your library. Then shuffle your library and put the last cards you looked at this way on top of it in any order.
        this.getSpellAbility().addEffect(new LimDulsVaultEffect());
    }

    public LimDulsVault(final LimDulsVault card) {
        super(card);
    }

    @Override
    public LimDulsVault copy() {
        return new LimDulsVault(this);
    }
}

class LimDulsVaultEffect extends OneShotEffect {

    public LimDulsVaultEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top five cards of your library. As many times as you choose, "
                + "you may pay 1 life, put those cards on the bottom of your library in any order, then look at the top five cards of your library. "
                + "Then shuffle your library and put the last cards you looked at this way on top of it in any order";
    }

    public LimDulsVaultEffect(final LimDulsVaultEffect effect) {
        super(effect);
    }

    @Override
    public LimDulsVaultEffect copy() {
        return new LimDulsVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean doAgain;
        do {
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
            player.lookAtCards(source, null, cards, game);
            doAgain = player.chooseUse(outcome, "Pay 1 life and look at the next 5 cards?", source, game);
            if (doAgain) {
                player.loseLife(1, game, false);
                player.putCardsOnBottomOfLibrary(cards, game, source, true);
            } else {
                player.shuffleLibrary(source, game);
                player.putCardsOnTopOfLibrary(cards, game, source, true);
            }
        } while (doAgain);

        return true;
    }
}
