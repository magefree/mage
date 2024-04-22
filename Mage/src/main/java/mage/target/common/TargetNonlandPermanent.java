package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetNonlandPermanent extends TargetPermanent {

    public TargetNonlandPermanent() {
        this(1);
    }

    public TargetNonlandPermanent(FilterNonlandPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetNonlandPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetNonlandPermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetNonlandPermanent(int minNumTargets, int maxNumTargets, boolean notTarget) {
        this(minNumTargets, maxNumTargets, (maxNumTargets > 1 ? StaticFilters.FILTER_PERMANENTS_NON_LAND : StaticFilters.FILTER_PERMANENT_NON_LAND), notTarget);
    }

    public TargetNonlandPermanent(int minNumTargets, int maxNumTargets, FilterNonlandPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetNonlandPermanent(final TargetNonlandPermanent target) {
        super(target);
    }

    @Override
    public TargetNonlandPermanent copy() {
        return new TargetNonlandPermanent(this);
    }
}
