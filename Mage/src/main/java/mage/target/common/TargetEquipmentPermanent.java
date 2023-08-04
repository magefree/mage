

package mage.target.common;

import mage.filter.common.FilterEquipmentPermanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public class TargetEquipmentPermanent extends TargetPermanent {

    public TargetEquipmentPermanent() {
        this(1, 1, new FilterEquipmentPermanent(), false);
    }

    public TargetEquipmentPermanent(FilterEquipmentPermanent filter) {
        this(1, 1, filter, false);
    }

    public TargetEquipmentPermanent(int numTargets) {
        this(numTargets, numTargets, new FilterEquipmentPermanent(), false);
    }

    public TargetEquipmentPermanent(int minNumTargets, int maxNumTargets, FilterEquipmentPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetEquipmentPermanent(final TargetEquipmentPermanent target) {
        super(target);
    }

    @Override
    public TargetEquipmentPermanent copy() {
        return new TargetEquipmentPermanent(this);
    }
}
