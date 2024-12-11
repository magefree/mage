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
        this(amount, minNumberOfTargets, defaultFilter);
    }

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets, FilterPermanent filter) {
        this(amount, minNumberOfTargets, amount, filter);
    }

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        this(amount, minNumberOfTargets, maxNumberOfTargets, defaultFilter);
    }

    public TargetCreaturePermanentAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets, FilterPermanent filter) {
        super(amount, minNumberOfTargets, maxNumberOfTargets, filter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount) {
        this(amount, defaultFilter);
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
