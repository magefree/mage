package mage.target.common;

import mage.filter.StaticFilters;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetControlledCreaturePermanent extends TargetControlledPermanent {

    public TargetControlledCreaturePermanent() {
        this(1);
    }

    public TargetControlledCreaturePermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetControlledCreaturePermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_CONTROLLED_CREATURES : StaticFilters.FILTER_CONTROLLED_CREATURE, false);
    }

    protected TargetControlledCreaturePermanent(final TargetControlledCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetControlledCreaturePermanent copy() {
        return new TargetControlledCreaturePermanent(this);
    }
}
