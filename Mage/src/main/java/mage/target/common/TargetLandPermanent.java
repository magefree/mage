
package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetLandPermanent extends TargetPermanent {

    public TargetLandPermanent() {
        this(1, 1, StaticFilters.FILTER_LAND, false);
    }

    public TargetLandPermanent(FilterLandPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetLandPermanent(int numTargets) {
        this(numTargets, numTargets, StaticFilters.FILTER_LAND, false);
    }

    public TargetLandPermanent(int numTargets, int maxNumTargets) {
        this(numTargets, maxNumTargets, StaticFilters.FILTER_LAND, false);
    }

    public TargetLandPermanent(int minNumTargets, int maxNumTargets, FilterLandPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetLandPermanent(final TargetLandPermanent target) {
        super(target);
    }

    @Override
    public TargetLandPermanent copy() {
        return new TargetLandPermanent(this);
    }
}
