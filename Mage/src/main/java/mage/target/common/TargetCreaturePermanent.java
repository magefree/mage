package mage.target.common;

import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCreaturePermanent extends TargetPermanent {

    public TargetCreaturePermanent() {
        this(1);
    }

    public TargetCreaturePermanent(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetCreaturePermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, maxNumTargets > 1 ? StaticFilters.FILTER_PERMANENT_CREATURES : StaticFilters.FILTER_PERMANENT_CREATURE, false);
    }

    protected TargetCreaturePermanent(final TargetCreaturePermanent target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanent copy() {
        return new TargetCreaturePermanent(this);
    }

}
