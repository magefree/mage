
package mage.cards.i;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class IllicitAuction extends CardImpl {

    public IllicitAuction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Each player may bid life for control of target creature. You start the bidding with a bid of 0. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and gains control of the creature.
        this.getSpellAbility().addEffect(new IllicitAuctionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private IllicitAuction(final IllicitAuction card) {
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

    private IllicitAuctionEffect(final IllicitAuctionEffect effect) {
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
        if (controller != null && targetCreature != null) {
            PlayerList playerList = game.getState().getPlayersInRange(controller.getId(), game);
            Player winner = game.getPlayer(controller.getId());
            int highBid = 0;
            game.informPlayers(winner.getLogName() + " has bet 0 lifes");

            Player currentPlayer = playerList.getNext(game, false);
            while (currentPlayer != null && !Objects.equals(currentPlayer, winner)) {
                String text = winner.getLogName() + " has bet " + highBid + " life" + (highBid > 1 ? "s" : "") + ". Top the bid?";
                if (currentPlayer.canRespond()
                        && currentPlayer.chooseUse(Outcome.GainControl, text, source, game)) {
                    int newBid = 0;
                    if (currentPlayer.isComputer()) {
                        // AI hint
                        // AI will evaluate the creature and bid
                        CreatureEvaluator eval = new CreatureEvaluator();
                        int computerLife = currentPlayer.getLife();
                        int creatureValue = eval.evaluate(targetCreature, game);
                        newBid = Math.max(creatureValue % 2, computerLife - 100);
                    } else {
                        if (currentPlayer.canRespond()) {
                            newBid = currentPlayer.getAmount(highBid + 1, Integer.MAX_VALUE, "Choose bid", game);
                        }
                    }
                    if (newBid > highBid) {
                        highBid = newBid;
                        winner = currentPlayer;
                        game.informPlayers(currentPlayer.getLogName() + " bet " + newBid + " life" + (newBid > 1 ? "s" : ""));
                    }
                }
                currentPlayer = playerList.getNext(game, false);

                // stops loop on all players quite
                if (game.getState().getPlayersInRange(controller.getId(), game).isEmpty()) {
                    break;
                }
            }

            game.informPlayers(winner.getLogName() + " won the auction with a bid of " + highBid + " life" + (highBid > 1 ? "s" : ""));
            winner.loseLife(highBid, game, source, false);
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
            if (creature.canAttack(null, game)) {
                value += 2;
            }
            value += creature.getPower().getValue();
            value += creature.getToughness().getValue();
            value += creature.getAbilities().getEvasionAbilities().size();
            value += creature.getAbilities().getProtectionAbilities().size();
            value += creature.getAbilities().containsKey(FirstStrikeAbility.getInstance().getId()) ? 1 : 0;
            value += creature.getAbilities().containsKey(DoubleStrikeAbility.getInstance().getId()) ? 2 : 0;
            value += creature.getAbilities().containsKey(TrampleAbility.getInstance().getId()) ? 1 : 0;
            values.put(creature.getId(), value);
        }
        return values.get(creature.getId());
    }

}
