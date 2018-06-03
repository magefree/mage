
package mage.target.common;

import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetControlledCreaturePermanent extends TargetControlledPermanent {

    public TargetControlledCreaturePermanent() {
        this(1, 1, new FilterControlledCreaturePermanent(), false);
    }

    public TargetControlledCreaturePermanent(int numTargets) {
        this(numTargets, numTargets, new FilterControlledCreaturePermanent(), false);
    }

    public TargetControlledCreaturePermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, new FilterControlledCreaturePermanent(), false);
    }

    public TargetControlledCreaturePermanent(FilterControlledCreaturePermanent filter) {
        super(1, 1, filter, false);
    }

    public TargetControlledCreaturePermanent(int minNumTargets, int maxNumTargets, FilterControlledCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetControlledCreaturePermanent(final TargetControlledCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetControlledCreaturePermanent copy() {
        return new TargetControlledCreaturePermanent(this);
    }
}
