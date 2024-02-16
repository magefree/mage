

package mage.target.common;

import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetNonBasicLandPermanent extends TargetLandPermanent {

    public TargetNonBasicLandPermanent() {
        this.filter = new FilterLandPermanent();
        this.filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        this.targetName = "nonbasic land";
    }

    protected TargetNonBasicLandPermanent(final TargetNonBasicLandPermanent target) {
        super(target);
    }

    @Override
    public TargetNonBasicLandPermanent copy() {
        return new TargetNonBasicLandPermanent(this);
    }
}
