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

    /**
     * <b>IMPORTANT</b>: Use more specific constructor if {@code amount} is not always the same number!<br>
     * {@code minNumberOfTargets} defaults to zero for {@code amount} > 3, otherwise to one, in line with typical templating.<br>
     * {@code maxNumberOfTargets} defaults to {@code amount}.
     *
     * @see TargetAnyTargetAmount#TargetAnyTargetAmount(int, int, int)
     */
    public TargetAnyTargetAmount(int amount) {
        this(amount, amount > 3 ? 0 : 1, amount);
    }

    /**
     * @param amount             Amount of stuff (e.g. damage) to distribute.
     * @param minNumberOfTargets Minimum number of targets.
     * @param maxNumberOfTargets Maximum number of targets. Should be set to {@code amount} if no lower max is needed.
     */
    public TargetAnyTargetAmount(int amount, int minNumberOfTargets, int maxNumberOfTargets) {
        this(StaticValue.get(amount), minNumberOfTargets, maxNumberOfTargets);
    }

    /**
     * {@code minNumberOfTargets} defaults to zero.<br>
     * {@code maxNumberOfTargets} defaults to Integer.MAX_VALUE.
     *
     * @see TargetAnyTargetAmount#TargetAnyTargetAmount(DynamicValue, int, int)
     */
    public TargetAnyTargetAmount(DynamicValue amount) {
        this(amount, 0, Integer.MAX_VALUE);
    }

    /**
     * @param amount             Amount of stuff (e.g. damage) to distribute.
     * @param minNumberOfTargets Minimum number of targets.
     * @param maxNumberOfTargets Maximum number of targets. Since {@code amount} is dynamic,
     *                           should be set to Integer.MAX_VALUE if no lower max is needed.
     *                           (Game will always prevent choosing more than {@code amount} targets.)
     */
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
