package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public class TargetAttackingOrBlockingCreature extends TargetPermanent {

    public TargetAttackingOrBlockingCreature() {
        this(1);
    }

    public TargetAttackingOrBlockingCreature(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetAttackingOrBlockingCreature(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURES : StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURE);
    }

    public TargetAttackingOrBlockingCreature(final TargetAttackingOrBlockingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingOrBlockingCreature copy() {
        return new TargetAttackingOrBlockingCreature(this);
    }
}
