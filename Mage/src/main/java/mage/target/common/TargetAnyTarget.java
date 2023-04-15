package mage.target.common;

import mage.filter.common.FilterAnyTarget;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public class TargetAnyTarget extends TargetPermanentOrPlayer {

    private static final FilterAnyTarget filter = new FilterAnyTarget();

    public TargetAnyTarget() {
        this(1);
    }

    public TargetAnyTarget(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetAnyTarget(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, filter, false);
    }

    protected TargetAnyTarget(final TargetAnyTarget target) {
        super(target);
    }

    @Override
    public TargetAnyTarget copy() {
        return new TargetAnyTarget(this);
    }
}
