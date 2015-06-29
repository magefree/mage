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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author LevelX2
 */
public class PainsReward extends CardImpl {

    public PainsReward(UUID ownerId) {
        super(ownerId, 85, "Pain's Reward", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "SOK";


        // Each player may bid life. You start the bidding with a bid of any number. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and draws four cards.
        this.getSpellAbility().addEffect(new PainsRewardEffect());
    }

    public PainsReward(final PainsReward card) {
        super(card);
    }

    @Override
    public PainsReward copy() {
        return new PainsReward(this);
    }
}


class PainsRewardEffect extends OneShotEffect {

    public PainsRewardEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player may bid life. You start the bidding with a bid of any number. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and draws four cards";
    }

    public PainsRewardEffect(final PainsRewardEffect effect) {
        super(effect);
    }

    @Override
    public PainsRewardEffect copy() {
        return new PainsRewardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(controller.getId());
            Player winner = game.getPlayer(controller.getId());

            int highBid = controller.getAmount(0, Integer.MAX_VALUE, "Choose amount of life to bid", game);
            game.informPlayers(winner.getLogName() + " has bet " + highBid + " lifes");

            Player currentPlayer = playerList.getNextInRange(controller, game);
            while (currentPlayer != winner) {
                String text = winner.getLogName() + " has bet " + highBid + " life" + (highBid > 1 ? "s" : "") + ". Top the bid?";
                if (currentPlayer.chooseUse(Outcome.Detriment, text, source, game)) {
                    int newBid = currentPlayer.getAmount(highBid + 1, Integer.MAX_VALUE, "Choose amount of life to bid", game);
                    if (newBid > highBid) {
                        highBid = newBid;
                        winner = currentPlayer;
                        game.informPlayers(currentPlayer.getLogName() + " bet " + newBid + " life" + (newBid > 1 ? "s" : ""));
                    }
                }
                currentPlayer = playerList.getNextInRange(controller, game);
            }

            game.informPlayers(winner.getLogName() + " won the auction with a bid of " + highBid + " life" + (highBid > 1 ? "s" : ""));
            winner.loseLife(highBid, game);
            winner.drawCards(4, game);
            return true;
        }
        return false;
    }
}
