package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 * @author ayratn
 */
public class TargetArtifactPermanent extends TargetPermanent {

    public TargetArtifactPermanent() {
        this(1);
    }

    public TargetArtifactPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetArtifactPermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets,
                (maxNumTargets > 1 ? StaticFilters.FILTER_PERMANENT_ARTIFACTS : StaticFilters.FILTER_PERMANENT_ARTIFACT),
                false);
    }

    public TargetArtifactPermanent(FilterArtifactPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetArtifactPermanent(int minNumTargets, int maxNumTargets, FilterArtifactPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetArtifactPermanent(final TargetArtifactPermanent target) {
        super(target);
    }

    @Override
    public TargetArtifactPermanent copy() {
        return new TargetArtifactPermanent(this);
    }

}
