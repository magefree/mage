package mage.target.common;

import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class TargetOpponentsCreaturePermanent extends TargetCreaturePermanent {

    public TargetOpponentsCreaturePermanent() {
        this(1, 1, new FilterOpponentsCreaturePermanent(), false);
    }

    public TargetOpponentsCreaturePermanent(int numTargets) {
        this(numTargets, numTargets, new FilterOpponentsCreaturePermanent(), false);
    }

    public TargetOpponentsCreaturePermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, new FilterOpponentsCreaturePermanent(), false);
    }

    public TargetOpponentsCreaturePermanent(FilterOpponentsCreaturePermanent filter) {
        super(1, 1, filter, false);
    }

    public TargetOpponentsCreaturePermanent(int minNumTargets, int maxNumTargets, FilterOpponentsCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
        this.targetName = filter.getMessage();
    }

    public TargetOpponentsCreaturePermanent(final TargetOpponentsCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetOpponentsCreaturePermanent copy() {
        return new TargetOpponentsCreaturePermanent(this);
    }
}
