
package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.constants.AbilityType;
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
public class SacrificeTargetCost extends CostImpl {

    private final List<Permanent> permanents = new ArrayList<>();

    public SacrificeTargetCost(TargetControlledPermanent target) {
        this.addTarget(target);
        target.setNotTarget(true); // sacrifice is never targeted
        target.setRequired(false); // can be canceled
        this.text = "sacrifice "
                + ((target.getNumberOfTargets() != 1
                || (target.getTargetName().startsWith("an")
                || target.getTargetName().startsWith("a ")))
                ? (target.getMinNumberOfTargets() == target.getMaxNumberOfTargets()
                && target.getMinNumberOfTargets() > 1
                ? CardUtil.numberToText(target.getNumberOfTargets()) +  " " : "")
                : (target.getTargetName().startsWith("artifact") ? "an " : "a "))
                + target.getTargetName();
        target.setTargetName(target.getTargetName() + " (to sacrifice)");
    }

    public SacrificeTargetCost(TargetControlledPermanent target, boolean noText) {
        this.addTarget(target);
    }

    public SacrificeTargetCost(SacrificeTargetCost cost) {
        super(cost);
        for (Permanent permanent : cost.permanents) {
            this.permanents.add(permanent.copy());
        }
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        UUID activator = controllerId;
        if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
            activator = ((ActivatedAbilityImpl) ability).getActivatorId();
        }
        // can be cancel by user
        if (targets.choose(Outcome.Sacrifice, activator, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                permanents.add(permanent.copy());
                paid |= permanent.sacrifice(sourceId, game);
            }
            if (!paid && targets.get(0).getNumberOfTargets() == 0) {
                paid = true; // e.g. for Devouring Rage
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        UUID activator = controllerId;
        if (ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.SPECIAL_ACTION) {
            if (((ActivatedAbilityImpl) ability).getActivatorId() != null) {
                activator = ((ActivatedAbilityImpl) ability).getActivatorId();
            } else {
                // Activator not filled?
                activator = controllerId;
            }
        }

        int validTargets = 0;
        int neededtargets = targets.get(0).getNumberOfTargets();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(((TargetControlledPermanent) targets.get(0)).getFilter(), controllerId, game)) {
            if (game.getPlayer(activator).canPaySacrificeCost(permanent, sourceId, controllerId, game)) {
                validTargets++;
                if (validTargets >= neededtargets) {
                    return true;
                }
            }
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
}
