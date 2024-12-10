package mage.target.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Zone;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class TargetAnyTargetAmount extends TargetPermanentOrPlayerAmount {

    private static final FilterPermanentOrPlayer defaultFilter
            = new FilterAnyTarget("targets");

    public TargetAnyTargetAmount(int amount, int minNumberOfTargets) {
        this(amount, minNumberOfTargets, amount);
    }

    public TargetAnyTargetAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        this(StaticValue.get(amount), minNumberOfTargets, maxNumberOfTargets);
    }

    public TargetAnyTargetAmount(DynamicValue amount) {
        this(amount, 0, 0);
    }

    public TargetAnyTargetAmount(DynamicValue amount, int minNumberOfTargets, int maxNumberOfTargets) {
        super(amount, minNumberOfTargets, maxNumberOfTargets);
        this.zone = Zone.ALL;
        this.filter = defaultFilter;
        this.targetName = filter.getMessage();
    }

    private TargetAnyTargetAmount(final TargetAnyTargetAmount target) {
        super(target);
        this.filter = target.filter.copy();
    }

    @Override
    public TargetAnyTargetAmount copy() {
        return new TargetAnyTargetAmount(this);
    }
}
