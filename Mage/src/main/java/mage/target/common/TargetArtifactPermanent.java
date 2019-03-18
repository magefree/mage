

package mage.target.common;

import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 * @author ayratn
 */
public class TargetArtifactPermanent extends TargetPermanent {
    
    public TargetArtifactPermanent() {
        this(1, 1, new FilterArtifactPermanent(), false);
    }

    
    public TargetArtifactPermanent(FilterArtifactPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetArtifactPermanent(int numTargets) {
        this(numTargets, numTargets, new FilterArtifactPermanent(), false);
    }

    public TargetArtifactPermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, new FilterArtifactPermanent(), false);
    }

    public TargetArtifactPermanent(int minNumTargets, int maxNumTargets, FilterArtifactPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetArtifactPermanent(final TargetArtifactPermanent target) {
        super(target);
    }

    @Override
    public TargetArtifactPermanent copy() {
        return new TargetArtifactPermanent(this);
    }

}
