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
 */
public class PermanentsOnTheBattlefieldCondition implements Condition {

    private final FilterPermanent filter;
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
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent localFilter;
        if (onlyControlled) {
            localFilter = filter.copy();
            localFilter.add(new ControllerIdPredicate(source.getControllerId()));
        } else {
            localFilter = filter;
        }
        return ComparisonType.compare(game.getBattlefield().count(
                localFilter, source.getControllerId(), source, game
        ), type, count);
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
