package mage.target.common;

import mage.filter.common.FilterOpponentOrPlaneswalker;

/**
 * @author LevelX2
 */
public class TargetOpponentOrPlaneswalker extends TargetPermanentOrPlayer {

    public TargetOpponentOrPlaneswalker() {
        this(1);
    }

    public TargetOpponentOrPlaneswalker(FilterOpponentOrPlaneswalker filter) {
        this(1, 1, filter, false);
    }

    public TargetOpponentOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets, new FilterOpponentOrPlaneswalker("opponent or planeswalker"), false);
    }

    public TargetOpponentOrPlaneswalker(int minNumTargets, int maxNumTargets, FilterOpponentOrPlaneswalker filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetOpponentOrPlaneswalker(final TargetOpponentOrPlaneswalker target) {
        super(target);
    }

    @Override
    public TargetOpponentOrPlaneswalker copy() {
        return new TargetOpponentOrPlaneswalker(this);
    }
}
