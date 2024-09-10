package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetControlledPermanent extends TargetPermanent {

    public TargetControlledPermanent() {
        this(1);
    }

    public TargetControlledPermanent(int numTargets) {
        this(numTargets, StaticFilters.FILTER_CONTROLLED_PERMANENT);
    }

    public TargetControlledPermanent(FilterControlledPermanent filter) {
        this(1, filter);
    }

    public TargetControlledPermanent(int numTargets, FilterControlledPermanent filter) {
        this(numTargets, numTargets, filter, false);
    }

    public TargetControlledPermanent(int minNumTargets, int maxNumTargets, FilterControlledPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetControlledPermanent(final TargetControlledPermanent target) {
        super(target);
    }

    @Override
    public TargetControlledPermanent copy() {
        return new TargetControlledPermanent(this);
    }
}
