package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
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

    public TapTargetCost(FilterControlledPermanent filter) {
        this(1, filter);
    }

    public TapTargetCost(int amount, FilterControlledPermanent filter) {
        this(amount, amount, filter);
    }

    public TapTargetCost(int minAmount, int maxAmount, FilterControlledPermanent filter) {
        this(new TargetControlledPermanent(minAmount, maxAmount, filter, true));
    }

    public TapTargetCost(TargetControlledPermanent target) {
        this.target = target;
        this.target.withNotTarget(true); // costs are never targeted
        this.target.setRequired(false); // can be cancel by user
        this.text = "tap " + (target.getMinNumberOfTargets() > 1
                ? CardUtil.numberToText(target.getMaxNumberOfTargets()) + ' ' + target.getTargetName()
                : CardUtil.addArticle(target.getTargetName()));
    }

    protected TapTargetCost(final TapTargetCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        List<Permanent> permanents = new ArrayList<>();
        if (target.getMaxNumberOfTargets() > 0 && target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(source, game);
                permanents.add(permanent);
            }
        }
        if (target.getMinNumberOfTargets() == 0) {
            paid = true; // e.g. Aryel with X = 0
        }
        source.getEffects().setValue("tappedPermanents", permanents);
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChooseOrAlreadyChosen(controllerId, source, game);
    }

    public TargetControlledPermanent getTarget() {
        return target;
    }

    @Override
    public TapTargetCost copy() {
        return new TapTargetCost(this);
    }
}
