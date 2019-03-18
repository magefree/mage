

package mage.target.common;

import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.TargetPermanent;

/**
 *
 * @author nantuko
 */
public class TargetAttackingOrBlockingCreature extends TargetPermanent {

    public TargetAttackingOrBlockingCreature() {
        this(1, 1, new FilterAttackingOrBlockingCreature(), false);
    }

    public TargetAttackingOrBlockingCreature(int numTargets) {
        this(numTargets, numTargets, new FilterAttackingOrBlockingCreature(), false);
    }

    public TargetAttackingOrBlockingCreature(int minNumTargets, int maxNumTargets, FilterAttackingOrBlockingCreature filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetAttackingOrBlockingCreature(final TargetAttackingOrBlockingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingOrBlockingCreature copy() {
        return new TargetAttackingOrBlockingCreature(this);
    }

}
