package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.SacrificeCost;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SacrificeTargetCost extends CostImpl implements SacrificeCost {

    private final List<Permanent> permanents = new ArrayList<>();

    /**
     * Sacrifice a permanent matching the filter:
     *
     * @param filter can be generic, will automatically add article and sacrifice predicates
     */
    public SacrificeTargetCost(FilterPermanent filter) {
        this(1, filter);
    }

    /**
     * Sacrifice N permanents matching the filter:
     *
     * @param filter can be generic, will automatically add sacrifice predicates
     */
    public SacrificeTargetCost(int numToSac, FilterPermanent filter) {
        this(new TargetSacrifice(numToSac, filter));
    }

    public SacrificeTargetCost(TargetSacrifice target) {
        this.addTarget(target);
        target.setRequired(false); // can be canceled
        this.text = "sacrifice " + makeText(target);
    }

    public SacrificeTargetCost(SacrificeTargetCost cost) {
        super(cost);
        for (Permanent permanent : cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        // can be cancelled by user
        if (this.getTargets().choose(Outcome.Sacrifice, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : this.getTargets().get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                addSacrificeTarget(game, permanent);
                paid |= permanent.sacrifice(source, game);
            }
            if (!paid && this.getTargets().get(0).getMinNumberOfTargets() == 0) {
                paid = true; // e.g. for Devouring Rage
            }
        }
        return paid;
    }

    /**
     * For storing additional info upon selecting permanents to sacrifice
     */
    protected void addSacrificeTarget(Game game, Permanent permanent) {
        permanents.add(permanent.copy());
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null){
            return false;
        }
        int validTargets = 0;
        int neededTargets = this.getTargets().get(0).getMinNumberOfTargets();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(((TargetPermanent) this.getTargets().get(0)).getFilter(), controllerId, source, game)) {
            if (controller.canPaySacrificeCost(permanent, source, controllerId, game)) {
                validTargets++;
                if (validTargets >= neededTargets) {
                    return true;
                }
            }
        }
        // solves issue #8097, if a sacrifice cost is optional and you don't have valid targets, then the cost can be paid
        return validTargets == 0 && this.getTargets().get(0).getMinNumberOfTargets() == 0;
    }

    @Override
    public SacrificeTargetCost copy() {
        return new SacrificeTargetCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

    private static String makeText(TargetSacrifice target) {
        if (target.getMinNumberOfTargets() != target.getMaxNumberOfTargets()) {
            return target.getTargetName();
        }
        if (target.getMinNumberOfTargets() == 1
                || target.getTargetName().startsWith("a ")
                || target.getTargetName().startsWith("an ")) {
            return CardUtil.addArticle(target.getTargetName());
        }
        return CardUtil.numberToText(target.getMinNumberOfTargets()) + ' ' + target.getTargetName();
    }
}
