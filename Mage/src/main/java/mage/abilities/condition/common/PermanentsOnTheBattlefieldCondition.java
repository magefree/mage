package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 * Battlefield checking condition. This condition can decorate other conditions
 * as well as be used standalone.
 *
 * @author nantuko
 * @author maurer.it_at_gmail.com
 * @see #Controls(mage.filter.Filter)
 * @see #Controls(mage.filter.Filter, mage.abilities.condition.Condition)
 */
public class PermanentsOnTheBattlefieldCondition implements Condition {

    private final FilterPermanent filter;
    private final Condition condition;
    private final ComparisonType type;
    private final int count;
    private final boolean onlyControlled;

    /**
     * Applies a filter and delegates creation to
     * {@link #ControlsPermanent(mage.filter.FilterPermanent, mage.abilities.condition.common.ControlsPermanent.CountType, int)}
     * with {@link ComparisonType#MORE_THAN}, and 0.
     *
     * @param filter
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter) {
        this(filter, true);
    }

    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, boolean onlyControlled) {
        this(filter, ComparisonType.MORE_THAN, 0, onlyControlled);
    }

    /**
     * Applies a filter, a {@link ComparisonType}, and count to permanents on
     * the battlefield when checking the condition during the
     * {@link #apply(mage.game.Game, mage.abilities.Ability) apply} method
     * invocation.
     *
     * @param filter
     * @param type
     * @param count
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count) {
        this(filter, type, count, true);
    }

    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count, boolean onlyControlled) {
        this.filter = filter;
        this.type = type;
        this.count = count;
        this.onlyControlled = onlyControlled;
        this.condition = null;
    }

    /**
     * Applies a filter, a {@link ComparisonType}, and count to permanents on
     * the battlefield and calls the decorated condition to see if it
     * {@link #apply(mage.game.Game, mage.abilities.Ability) applies} as well.
     * This will force both conditions to apply for this to be true.
     *
     * @param filter
     * @param type
     * @param count
     * @param conditionToDecorate
     */
    public PermanentsOnTheBattlefieldCondition(FilterPermanent filter, ComparisonType type, int count, Condition conditionToDecorate) {
        this.filter = filter;
        this.type = type;
        this.count = count;
        this.onlyControlled = true;
        this.condition = conditionToDecorate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies;

        FilterPermanent localFilter = filter.copy();
        if (onlyControlled) {
            localFilter.add(new ControllerIdPredicate(source.getControllerId()));
        }

        int permanentsOnBattlefield = game.getBattlefield().count(localFilter, source.getSourceId(), source.getControllerId(), game);
        conditionApplies = ComparisonType.compare(permanentsOnBattlefield, type, count);

        //If a decorated condition exists, check it as well and apply them together.
        if (this.condition != null) {
            conditionApplies = conditionApplies && this.condition.apply(game, source);
        }

        return conditionApplies;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
