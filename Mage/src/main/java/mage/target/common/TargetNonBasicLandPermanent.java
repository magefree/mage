

package mage.target.common;

import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetNonBasicLandPermanent extends TargetPermanent {

    private static final FilterLandPermanent filter = new FilterLandPermanent("nonbasic land");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public TargetNonBasicLandPermanent() {
        this(1, 1);
    }

    public TargetNonBasicLandPermanent(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, filter, false);
    }

    protected TargetNonBasicLandPermanent(final TargetNonBasicLandPermanent target) {
        super(target);
    }

    @Override
    public TargetNonBasicLandPermanent copy() {
        return new TargetNonBasicLandPermanent(this);
    }
}
