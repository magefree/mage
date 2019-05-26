package mage.target.common;

import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class TargetPlaneswalkerPermanent extends TargetPermanent {

    public TargetPlaneswalkerPermanent() {
        this(1, 1, StaticFilters.FILTER_PERMANENT_PLANESWALKER, false);
    }

    public TargetPlaneswalkerPermanent(FilterPlaneswalkerPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetPlaneswalkerPermanent(int numTargets) {
        this(numTargets, numTargets, StaticFilters.FILTER_PERMANENT_PLANESWALKER, false);
    }

    public TargetPlaneswalkerPermanent(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, StaticFilters.FILTER_PERMANENT_PLANESWALKER, false);
    }

    public TargetPlaneswalkerPermanent(int minNumTargets, int maxNumTargets, FilterPlaneswalkerPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    private TargetPlaneswalkerPermanent(final TargetPlaneswalkerPermanent target) {
        super(target);
    }

    @Override
    public TargetPlaneswalkerPermanent copy() {
        return new TargetPlaneswalkerPermanent(this);
    }
}
