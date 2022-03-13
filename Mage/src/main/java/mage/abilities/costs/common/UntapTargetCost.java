
package mage.abilities.costs.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

import java.util.List;
import java.util.UUID;
import mage.abilities.costs.Cost;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class UntapTargetCost extends CostImpl {

    TargetControlledPermanent target;

    public UntapTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Untap " + CardUtil.numberToText(target.getMaxNumberOfTargets(), "") + ' ' + target.getTargetName();
    }

    public UntapTargetCost(final UntapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (target.choose(Outcome.Untap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : (List<UUID>) target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.untap(game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    @Override
    public UntapTargetCost copy() {
        return new UntapTargetCost(this);
    }

}
