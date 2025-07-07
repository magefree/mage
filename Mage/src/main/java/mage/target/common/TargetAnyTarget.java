package mage.target.common;

import mage.filter.common.FilterAnyTarget;

/**
 * Warning, it's a target for damage effects only (ignore lands, artifacts and other non-damageable objects)
 *
 * @author JRHerlehy Created on 4/8/18.
 */
public class TargetAnyTarget extends TargetPermanentOrPlayer {

    private static final FilterAnyTarget defaultFilter = new FilterAnyTarget();

    public TargetAnyTarget() {
        this(1);
    }

    public TargetAnyTarget(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetAnyTarget(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, defaultFilter, false);
    }

    protected TargetAnyTarget(final TargetAnyTarget target) {
        super(target);
    }

    @Override
    public TargetAnyTarget copy() {
        return new TargetAnyTarget(this);
    }
}
