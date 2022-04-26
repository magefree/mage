package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public class TargetOpponentsCreaturePermanent extends TargetPermanent {

    public TargetOpponentsCreaturePermanent() {
        this(1);
    }

    public TargetOpponentsCreaturePermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetOpponentsCreaturePermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES : StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
    }

    public TargetOpponentsCreaturePermanent(final TargetOpponentsCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetOpponentsCreaturePermanent copy() {
        return new TargetOpponentsCreaturePermanent(this);
    }
}
