/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.PlayerCanGainLifePredicate;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class GainLifeOpponentCost extends CostImpl<GainLifeOpponentCost> {

    private static final FilterPlayer filter = new FilterPlayer("opponent that can gain life");
    
    static {
        filter.add(new PlayerPredicate(TargetController.OPPONENT));
        filter.add(new PlayerCanGainLifePredicate());
    }
    
    private final int amount;

    public GainLifeOpponentCost(int amount) {
        this.amount = amount;
        this.text = new StringBuilder("you may have an opponent gain ").append(amount).append(" life").toString();
    }

    public GainLifeOpponentCost(GainLifeOpponentCost cost) {
        super(cost);
        this.amount = cost.amount;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
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
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetPlayer target = new TargetPlayer(1, 1, true, filter);
            if (controller.chooseTarget(Outcome.Detriment, target, ability, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    opponent.gainLife(amount, game);
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
