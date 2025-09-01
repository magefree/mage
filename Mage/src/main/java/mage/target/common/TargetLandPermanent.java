
package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetLandPermanent extends TargetPermanent {

    public TargetLandPermanent() {
        this(1);
    }

    public TargetLandPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetLandPermanent(int numTargets, int maxNumTargets) {
        super(numTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_LANDS : StaticFilters.FILTER_LAND, false);
    }

    protected TargetLandPermanent(final TargetLandPermanent target) {
        super(target);
    }

    @Override
    public TargetLandPermanent copy() {
        return new TargetLandPermanent(this);
    }
}
