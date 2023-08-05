package mage.target.common;

import mage.filter.common.FilterPlayerOrPlaneswalker;

/**
 * @author LevelX2
 */
public class TargetPlayerOrPlaneswalker extends TargetPermanentOrPlayer {

    public TargetPlayerOrPlaneswalker() {
        this(1, 1, new FilterPlayerOrPlaneswalker(), false);
    }

    public TargetPlayerOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets, new FilterPlayerOrPlaneswalker(), false);
    }

    public TargetPlayerOrPlaneswalker(FilterPlayerOrPlaneswalker filter) {
        this(1, 1, filter, false);
    }

    public TargetPlayerOrPlaneswalker(int minNumTargets, int maxNumTargets, FilterPlayerOrPlaneswalker filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetPlayerOrPlaneswalker(final TargetPlayerOrPlaneswalker target) {
        super(target);
    }

    @Override
    public TargetPlayerOrPlaneswalker copy() {
        return new TargetPlayerOrPlaneswalker(this);
    }
}
