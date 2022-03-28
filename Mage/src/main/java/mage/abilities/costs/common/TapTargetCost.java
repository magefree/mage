package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TapTargetCost extends CostImpl {

    TargetControlledPermanent target;

    public TapTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.target.setNotTarget(true); // costs are never targeted
        this.target.setRequired(false); // can be cancel by user
        this.text = "tap " + (target.getNumberOfTargets() > 1
                ? CardUtil.numberToText(target.getMaxNumberOfTargets()) + ' ' + target.getTargetName()
                : CardUtil.addArticle(target.getTargetName()));
    }

    public TapTargetCost(final TapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        List<Permanent> permanents = new ArrayList<>();
        if (target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(source, game);
                permanents.add(permanent);
            }
        }
        source.getEffects().setValue("tappedPermanents", permanents);
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    public TargetControlledPermanent getTarget() {
        return target;
    }

    @Override
    public TapTargetCost copy() {
        return new TapTargetCost(this);
    }

}
