
package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreaturePermanent extends TargetPermanent {

    public TargetCreaturePermanent() {
        this(1);
    }

    public TargetCreaturePermanent(FilterCreaturePermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetCreaturePermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetCreaturePermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_PERMANENT_CREATURES : StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    public TargetCreaturePermanent(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetCreaturePermanent(final TargetCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanent copy() {
        return new TargetCreaturePermanent(this);
    }

}
