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
package mage.cards.i;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class IllicitAuction extends CardImpl {

    public IllicitAuction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Each player may bid life for control of target creature. You start the bidding with a bid of 0. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and gains control of the creature.
        this.getSpellAbility().addEffect(new IllicitAuctionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public IllicitAuction(final IllicitAuction card) {
        super(card);
    }

    @Override
    public IllicitAuction copy() {
        return new IllicitAuction(this);
    }
}

// effect is based on GainControlTargetEffect
class IllicitAuctionEffect extends GainControlTargetEffect {

    public IllicitAuctionEffect() {
        super(Duration.EndOfGame);
        this.staticText = "Each player may bid life for control of target creature. You start the bidding with a bid of 0. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and gains control of the creature.";
    }

    public IllicitAuctionEffect(final IllicitAuctionEffect effect) {
        super(effect);
    }

    @Override
    public IllicitAuctionEffect copy() {
        return new IllicitAuctionEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (controller != null
                && targetCreature != null) {
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(game.getActivePlayerId());

            Player winner = game.getPlayer(game.getActivePlayerId());
            int highBid = 0;
            game.informPlayers(winner.getLogName() + " has bet 0 lifes");
            Player currentPlayer = playerList.getNextInRange(controller, game);
            while (!Objects.equals(currentPlayer, winner)) {
                String text = winner.getLogName() + " has bet " + highBid + " life" + (highBid > 1 ? "s" : "") + ". Top the bid?";
                if (currentPlayer.chooseUse(Outcome.GainControl, text, source, game)) {
                    int newBid = 0;
                    if (!currentPlayer.isHuman()) {//AI will evaluate the creature and bid
                        CreatureEvaluator eval = new CreatureEvaluator();
                        int computerLife = currentPlayer.getLife();
                        int creatureValue = eval.evaluate(targetCreature, game);
                        newBid = Math.max(creatureValue % 2, computerLife - 100);
                    } else {
                        newBid = currentPlayer.getAmount(highBid + 1, Integer.MAX_VALUE, "Choose bid", game);
                    }
                    if (newBid > highBid) {
                        highBid = newBid;
                        winner = currentPlayer;
                        game.informPlayers(currentPlayer.getLogName() + " bet " + newBid + " life" + (newBid > 1 ? "s" : ""));
                    }
                }
                currentPlayer = playerList.getNextInRange(controller, game);
            }

            game.informPlayers(winner.getLogName() + " won the auction with a bid of " + highBid + " life" + (highBid > 1 ? "s" : ""));
            winner.loseLife(highBid, game, false);
            super.controllingPlayerId = winner.getId();
        }
        super.init(source, game);
    }
}

class CreatureEvaluator {

    private Map<UUID, Integer> values = new HashMap<>();

    public int evaluate(Permanent creature, Game game) {
        if (!values.containsKey(creature.getId())) {
            int value = 0;
            if (creature.canAttack(game))
                value += 2;
            value += creature.getPower().getValue();
            value += creature.getToughness().getValue();
            value += creature.getAbilities().getEvasionAbilities().size();
            value += creature.getAbilities().getProtectionAbilities().size();
            value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId())?1:0;
            value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId())?2:0;
            value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId())?1:0;
            values.put(creature.getId(), value);
        }
        return values.get(creature.getId());
    }

}