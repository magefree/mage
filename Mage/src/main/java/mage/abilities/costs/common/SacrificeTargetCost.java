package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.SacrificeCost;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
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

    public SacrificeTargetCost(FilterPermanent filter) {
        this(new TargetSacrifice(filter));
    }

    @Deprecated
    public SacrificeTargetCost(TargetControlledPermanent target) {
        // TODO: replace all with TargetSacrifice, 110 usages remaining
        this.addTarget(target);
        target.withNotTarget(true); // sacrifice is never targeted
        target.setRequired(false); // can be canceled
        this.text = "sacrifice " + makeText(target);
        target.setTargetName(target.getTargetName() + " (to sacrifice)");
    }

    public SacrificeTargetCost(TargetSacrifice target) {
        this.addTarget(target);
        target.setRequired(false); // can be canceled
        this.text = "sacrifice " + makeText(target);
        target.setTargetName(target.getTargetName() + " (to sacrifice)");
    }

    public SacrificeTargetCost(SacrificeTargetCost cost) {
        super(cost);
        for (Permanent permanent : cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        UUID activator = controllerId;
        if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
            activator = ((ActivatedAbilityImpl) ability).getActivatorId();
        }
        // can be cancel by user
        if (this.getTargets().choose(Outcome.Sacrifice, activator, source.getSourceId(), source, game)) {
            for (UUID targetId : this.getTargets().get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                addSacrificeTarget(game, permanent);
                paid |= permanent.sacrifice(source, game);
            }
            if (!paid && this.getTargets().get(0).getNumberOfTargets() == 0) {
                paid = true; // e.g. for Devouring Rage
            }
        }
        return paid;
    }

    protected void addSacrificeTarget(Game game, Permanent permanent) {
        permanents.add(permanent.copy());
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        UUID activator = controllerId;
        if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
            if (((ActivatedAbilityImpl) ability).getActivatorId() != null) {
                activator = ((ActivatedAbilityImpl) ability).getActivatorId();
            }  // else, Activator not filled?
        }

        int validTargets = 0;
        int neededTargets = this.getTargets().get(0).getNumberOfTargets();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(((TargetPermanent) this.getTargets().get(0)).getFilter(), controllerId, game)) {
            if (game.getPlayer(activator).canPaySacrificeCost(permanent, source, controllerId, game)) {
                validTargets++;
                if (validTargets >= neededTargets) {
                    return true;
                }
            }
        }
        // solves issue #8097, if a sacrifice cost is optional and you don't have valid targets, then the cost can be paid
        if (validTargets == 0 && this.getTargets().get(0).getMinNumberOfTargets() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public SacrificeTargetCost copy() {
        return new SacrificeTargetCost(this);
    }

    public List<Permanent> getPermanents() {
        return permanents;
    }

    private static String makeText(TargetControlledPermanent target) {
        if (target.getMinNumberOfTargets() != target.getMaxNumberOfTargets()) {
            return target.getTargetName();
        }
        if (target.getNumberOfTargets() == 1
                || target.getTargetName().startsWith("a ")
                || target.getTargetName().startsWith("an ")) {
            return CardUtil.addArticle(target.getTargetName());
        }
        return CardUtil.numberToText(target.getNumberOfTargets()) + ' ' + target.getTargetName();
    }

    private static String makeText(TargetSacrifice target) {
        if (target.getMinNumberOfTargets() != target.getMaxNumberOfTargets()) {
            return target.getTargetName();
        }
        if (target.getNumberOfTargets() == 1
                || target.getTargetName().startsWith("a ")
                || target.getTargetName().startsWith("an ")) {
            return CardUtil.addArticle(target.getTargetName());
        }
        return CardUtil.numberToText(target.getNumberOfTargets()) + ' ' + target.getTargetName();
    }
}
