package mage.target.common;

import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public class TargetControlledLandPermanent extends TargetControlledPermanent {

    public TargetControlledLandPermanent() {
        this(1);
    }

    public TargetControlledLandPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetControlledLandPermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS : StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, false);
    }

    protected TargetControlledLandPermanent(final TargetControlledLandPermanent target) {
        super(target);
    }

    @Override
    public TargetControlledLandPermanent copy() {
        return new TargetControlledLandPermanent(this);
    }
}
