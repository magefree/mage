package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author North
 */
public class TargetCreaturePermanentAmount extends TargetPermanentAmount {

    private static final FilterCreaturePermanent defaultFilter = new FilterCreaturePermanent("target creatures");

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets) {
        super(amount, minNumberOfTargets, defaultFilter);
    }

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        super(amount, minNumberOfTargets, maxNumberOfTargets, defaultFilter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount) {
        super(amount, 0, defaultFilter);
    }

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets, FilterPermanent filter) {
        super(amount, minNumberOfTargets, filter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount, FilterPermanent filter) {
        super(amount, 0, filter);
    }

    private TargetCreaturePermanentAmount(final TargetCreaturePermanentAmount target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanentAmount copy() {
        return new TargetCreaturePermanentAmount(this);
    }
}
