

package mage.target.common;

import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAttackingCreature extends TargetPermanent {

    public TargetAttackingCreature() {
        this(1, 1, new FilterAttackingCreature(), false);
    }
    
    public TargetAttackingCreature(int numTargets) {
        this(numTargets, numTargets, new FilterAttackingCreature(), false);
    }

    public TargetAttackingCreature(int minNumTargets, int maxNumTargets, FilterAttackingCreature filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    public TargetAttackingCreature(final TargetAttackingCreature target) {
        super(target);
    }

    @Override
    public TargetAttackingCreature copy() {
        return new TargetAttackingCreature(this);
    }

}
