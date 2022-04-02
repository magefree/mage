package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.TargetPermanent;

/**
 *
 * @author nantuko
 */
public class TargetAttackingOrBlockingCreature extends TargetPermanent {

    public TargetAttackingOrBlockingCreature() {
        this(1, 1, StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURE, false);
    }

    public TargetAttackingOrBlockingCreature(int numTargets) {
        this(numTargets, numTargets, StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURE, false);
    }

    public TargetAttackingOrBlockingCreature(int minNumTargets, int maxNumTargets, FilterAttackingOrBlockingCreature filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetAttackingOrBlockingCreature(final TargetAttackingOrBlockingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingOrBlockingCreature copy() {
        return new TargetAttackingOrBlockingCreature(this);
    }

}
