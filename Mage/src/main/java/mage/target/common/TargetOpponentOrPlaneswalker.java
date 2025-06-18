package mage.target.common;

import mage.filter.common.FilterOpponentOrPlaneswalker;
import mage.filter.common.FilterPermanentOrPlayer;

/**
 * @author LevelX2
 */
public class TargetOpponentOrPlaneswalker extends TargetPermanentOrPlayer {

    private static final FilterPermanentOrPlayer filter = new FilterOpponentOrPlaneswalker();

    public TargetOpponentOrPlaneswalker() {
        this(1);
    }

    public TargetOpponentOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetOpponentOrPlaneswalker(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, false);
    }

    public TargetOpponentOrPlaneswalker(int minNumTargets, int maxNumTargets, boolean notTarget) {
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
