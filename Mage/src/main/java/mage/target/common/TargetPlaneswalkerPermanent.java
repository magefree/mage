package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class TargetPlaneswalkerPermanent extends TargetPermanent {

    public TargetPlaneswalkerPermanent() {
        this(1);
    }

    public TargetPlaneswalkerPermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetPlaneswalkerPermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, StaticFilters.FILTER_PERMANENT_PLANESWALKER, false);
    }

    private TargetPlaneswalkerPermanent(final TargetPlaneswalkerPermanent target) {
        super(target);
    }

    @Override
    public TargetPlaneswalkerPermanent copy() {
        return new TargetPlaneswalkerPermanent(this);
    }
}
