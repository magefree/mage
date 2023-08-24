package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Susucr
 */
public class TargetUpToCreaturePermanentAmount extends TargetPermanentAmount {

    private static final FilterCreaturePermanent defaultFilter = new FilterCreaturePermanent("target creatures");

    public TargetUpToCreaturePermanentAmount(int amount) {
        this(amount, defaultFilter);
    }

    public TargetUpToCreaturePermanentAmount(int amount, FilterPermanent filter) {
        super(amount, filter);
        this.setMinNumberOfTargets(0);
        this.setMaxNumberOfTargets(amount);
    }

    public TargetUpToCreaturePermanentAmount(DynamicValue amount) {
        this(amount, defaultFilter);
    }

    public TargetUpToCreaturePermanentAmount(DynamicValue amount, FilterPermanent filter) {
        super(amount, filter);
        this.setMinNumberOfTargets(0);
        this.setMaxNumberOfTargets(Integer.MAX_VALUE);
    }

    private TargetUpToCreaturePermanentAmount(final TargetUpToCreaturePermanentAmount target) {
        super(target);
    }

    @Override
    public TargetUpToCreaturePermanentAmount copy() {
        return new TargetUpToCreaturePermanentAmount(this);
    }
}
