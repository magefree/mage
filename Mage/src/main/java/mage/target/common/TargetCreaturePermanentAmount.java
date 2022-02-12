package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author North
 */
public class TargetCreaturePermanentAmount extends TargetPermanentAmount {

    private static final FilterCreaturePermanent defaultFilter = new FilterCreaturePermanent("target creatures");

    public TargetCreaturePermanentAmount(int amount) {
        super(amount, defaultFilter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount) {
        this(amount, defaultFilter);
    }

    public TargetCreaturePermanentAmount(int amount, FilterPermanent filter) {
        super(amount, filter);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount, FilterPermanent filter) {
        super(amount, filter);
    }

    private TargetCreaturePermanentAmount(final TargetCreaturePermanentAmount target) {
        super(target);
    }

    @Override
    public TargetCreaturePermanentAmount copy() {
        return new TargetCreaturePermanentAmount(this);
    }
}
