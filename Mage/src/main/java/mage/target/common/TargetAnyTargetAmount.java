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

    public TargetAnyTargetAmount(int amount) {
        this(amount, 0);
    }

    public TargetAnyTargetAmount(int amount, int maxNumberOfTargets) {
        // 107.1c If a rule or ability instructs a player to choose “any number,” that player may choose
        // any positive number or zero, unless something (such as damage or counters) is being divided
        // or distributed among “any number” of players and/or objects. In that case, a nonzero number
        // of players and/or objects must be chosen if possible.
        this(StaticValue.get(amount), maxNumberOfTargets);
        this.minNumberOfTargets = 1;
    }

    public TargetAnyTargetAmount(DynamicValue amount) {
        this(amount, 0);
    }

    public TargetAnyTargetAmount(DynamicValue amount, int maxNumberOfTargets) {
        super(amount, maxNumberOfTargets);
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
