package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAttackingCreature extends TargetPermanent {

    public TargetAttackingCreature() {
        this(1);
    }

    public TargetAttackingCreature(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetAttackingCreature(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetAttackingCreature(int minNumTargets, int maxNumTargets, boolean notTarget) {
        super(minNumTargets, maxNumTargets, StaticFilters.FILTER_ATTACKING_CREATURE, notTarget);
    }

    public TargetAttackingCreature(final TargetAttackingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingCreature copy() {
        return new TargetAttackingCreature(this);
    }
}
