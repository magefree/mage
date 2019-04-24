
package mage.abilities.costs.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TapTargetCost extends CostImpl {

    TargetControlledPermanent target;

    public TapTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.target.setNotTarget(true); // costs are never targeted
        this.text
                = new StringBuilder("Tap ")
                .append((target.getTargetName().startsWith("a ") || target.getTargetName().startsWith("an ") || target.getTargetName().startsWith("another"))
                        ? "" : CardUtil.numberToText(target.getMaxNumberOfTargets()) + ' ')
                .append(target.getTargetName()).toString();
    }

    public TapTargetCost(final TapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(sourceId, controllerId, game);
    }
    
    public TargetControlledPermanent getTarget() {
        return target;
    }

    @Override
    public TapTargetCost copy() {
        return new TapTargetCost(this);
    }

}
