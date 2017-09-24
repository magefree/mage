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
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class GoblinGame extends CardImpl {

    public GoblinGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Each player hides at least one item, then all players reveal them simultaneously. Each player loses life equal to the number of items he or she revealed. The player who revealed the fewest items then loses half his or her life, rounded up. If two or more players are tied for fewest, each loses half his or her life, rounded up.
        // Reinterpreted as: Each player secretly chooses a number greater than 0. Then those numbers are revealed. Each player loses life equal to his or her chosen number. The player who revealed the lowest number then loses half his or her life, rounded up. If two or more players are tied for lowest, each loses half his or her life, rounded up.
        this.getSpellAbility().addEffect(new GoblinGameEffect());

    }

    public GoblinGame(final GoblinGame card) {
        super(card);
    }

    @Override
    public GoblinGame copy() {
        return new GoblinGame(this);
    }
}

class GoblinGameEffect extends OneShotEffect {

    public GoblinGameEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player hides at least one item, then all players reveal them simultaneously. Each player loses life equal to the number of items he or she revealed. The player who revealed the fewest items then loses half his or her life, rounded up. If two or more players are tied for fewest, each loses half his or her life, rounded up.";
    }

    public GoblinGameEffect(final GoblinGameEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGameEffect copy() {
        return new GoblinGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lowestNumber = 0;
        int number = 0;
        String message = "Choose a number of objects to hide.";
        Map<Player, Integer> numberChosen = new HashMap<>();

        //players choose numbers
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                // TODO: consider changing 1000 to another cap, or even Integer.MAX_VALUE if the Volcano Hellion binary wraparound gets addressed (although hiding over two billions of items would be rather difficult IRL)
                number = player.getAmount(1, 1000, message, game);
                numberChosen.put(player, number);
            }
        }
        //get lowest number
        for (Player player : numberChosen.keySet()) {
            if (lowestNumber == 0 || lowestNumber > numberChosen.get(player)) {
                lowestNumber = numberChosen.get(player);
            }
        }
        //reveal numbers to players and follow through with effects
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                game.informPlayers(player.getLogName() + " chose number " + numberChosen.get(player));
                player.loseLife(numberChosen.get(player), game, false);
            }
        }
        for (Player player : game.getPlayers().values()) {
            if (player != null) {
                if (numberChosen.get(player) <= lowestNumber) {
                    game.informPlayers(player.getLogName() + " chose the lowest number");
                    Integer amount = (int) Math.ceil(player.getLife() / 2f);
                    if (amount > 0) {
                        player.loseLife(amount, game, false);
                    }
                }
            }
        }
        return true;
    }
}
