package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

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
        this(minNumTargets, maxNumTargets, StaticFilters.FILTER_CONTROLLED_CREATURE, false);
    }

    public TargetControlledCreaturePermanent(FilterControlledCreaturePermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetControlledCreaturePermanent(int minNumTargets, int maxNumTargets, FilterControlledCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetControlledCreaturePermanent(final TargetControlledCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetControlledCreaturePermanent copy() {
        return new TargetControlledCreaturePermanent(this);
    }
}
