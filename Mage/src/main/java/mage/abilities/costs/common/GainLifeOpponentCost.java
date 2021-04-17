package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.PlayerCanGainLifePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class GainLifeOpponentCost extends CostImpl {

    private static final FilterPlayer filter = new FilterPlayer("opponent that can gain life");

    static {
        filter.add(TargetController.OPPONENT.getPlayerPredicate());
        filter.add(new PlayerCanGainLifePredicate()); // you can't pay the costs by letting a player gain life that can't get life by rule changing effect
    }

    private final int amount;

    public GainLifeOpponentCost(int amount) {
        this.amount = amount;
        this.text = "an opponent gains " + amount + " life";
    }

    public GainLifeOpponentCost(GainLifeOpponentCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            for (UUID opponentId : game.getOpponents(controllerId)) {
                Player player = game.getPlayer(opponentId);
                if (player != null && player.isCanGainLife()) {
                    // at least one opponent must be able to gain life
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetPlayer target = new TargetPlayer(1, 1, true, filter);
            if (controller.chooseTarget(Outcome.Detriment, target, ability, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    opponent.gainLife(amount, game, source);
                    paid = true;

                }

            }
        }
        return paid;
    }

    @Override
    public GainLifeOpponentCost copy() {
        return new GainLifeOpponentCost(this);
    }

}
