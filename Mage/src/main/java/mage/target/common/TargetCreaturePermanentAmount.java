package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

/**
 * @author North
 */
public class TargetCreaturePermanentAmount extends TargetPermanentAmount {

    public TargetCreaturePermanentAmount(int amount) {
        super(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public TargetCreaturePermanentAmount(DynamicValue amount) {
        this(amount, StaticFilters.FILTER_PERMANENT_CREATURE);
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
