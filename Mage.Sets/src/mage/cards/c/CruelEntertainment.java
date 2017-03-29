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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CruelEntertainment extends CardImpl {

    public CruelEntertainment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Choose target player and another target player. The first player controls the second player during the second player's next turn, and the second player controls the first player during the first player's next turn.
        this.getSpellAbility().addEffect(new CruelEntertainmentEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));

    }

    public CruelEntertainment(final CruelEntertainment card) {
        super(card);
    }

    @Override
    public CruelEntertainment copy() {
        return new CruelEntertainment(this);
    }
}

class CruelEntertainmentEffect extends OneShotEffect {

    public CruelEntertainmentEffect() {
        super(Outcome.Detriment);
        this.staticText = "Choose target player and another target player. The first player controls the second player"
                + " during the second player's next turn, and the second player controls the first player during the first player's next turn";
    }

    public CruelEntertainmentEffect(final CruelEntertainmentEffect effect) {
        super(effect);
    }

    @Override
    public CruelEntertainmentEffect copy() {
        return new CruelEntertainmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player player2 = null;
        if (getTargetPointer().getTargets(game, source).size() > 1) {
            player2 = game.getPlayer(getTargetPointer().getTargets(game, source).get(1));
        }
        if (player1 != null && player2 != null) {
            game.getState().getTurnMods().add(new TurnMod(player1.getId(), player2.getId()));
            game.getState().getTurnMods().add(new TurnMod(player2.getId(), player1.getId()));
            return true;
        }
        return false;
    }
}
