

package mage.abilities.costs.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public class ExileTargetCost extends CostImpl {

    List<Permanent> permanents = new ArrayList<>();

    public ExileTargetCost(TargetControlledPermanent target) {
        this.addTarget(target);
        this.text = "Exile " + target.getTargetName();
    }
    
    public ExileTargetCost(TargetControlledPermanent target, boolean noText) {
        this.addTarget(target);
    }

    public ExileTargetCost(ExileTargetCost cost) {
        super(cost);
        for (Permanent permanent: cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (targets.choose(Outcome.Exile, controllerId, source.getSourceId(), game)) {
            for (UUID targetId: targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                permanents.add(permanent.copy());
                // 117.11. The actions performed when paying a cost may be modified by effects. 
                // Even if they are, meaning the actions that are performed don't match the actions 
                // that are called for, the cost has still been paid.
                // so return state here is not important because the user indended to exile the target anyway
                game.getPlayer(ability.getControllerId()).moveCardToExileWithInfo(permanent, null, null, source, game, Zone.BATTLEFIELD, true);
            }
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(source.getSourceId(), controllerId, game);
    }

    @Override
    public ExileTargetCost copy() {
        return new ExileTargetCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

}
