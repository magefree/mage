package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public class UntapTargetCost extends CostImpl {

    private final TargetControlledPermanent target;

    public UntapTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = makeText(target);
    }

    public UntapTargetCost(final UntapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!target.choose(Outcome.Untap, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            paid |= permanent.untap(game);
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

    private static String makeText(TargetControlledPermanent target) {
        StringBuilder sb = new StringBuilder("untap ");
        if (target.getMaxNumberOfTargets() > 1) {
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
            sb.append(' ');
            sb.append(target.getTargetName().replace(" you control", "s you control"));
        } else {
            sb.append(CardUtil.addArticle(target.getTargetName()));
        }
        return sb.toString();
    }
}
