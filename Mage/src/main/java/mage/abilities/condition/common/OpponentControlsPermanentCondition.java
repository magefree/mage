

package mage.abilities.condition.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

/**
 * Checks if one opponent (each opponent is checked on its own) fulfills
 * the defined condition of controlling the defined number of permanents.
 *
 * @author LevelX2
 */

public class OpponentControlsPermanentCondition implements Condition {


    private FilterPermanent filter;
    private ComparisonType type;
    private int count;

    /**
     * @param filter
     */
    public OpponentControlsPermanentCondition(FilterPermanent filter) {
        this(filter, ComparisonType.MORE_THAN, 0);
    }

    /**
     * Applies a filter, a {@link ComparisonType}, and count to permanents on the
     * battlefield when checking the condition during the
     * {@link #apply(mage.game.Game, mage.abilities.Ability) apply} method invocation.
     *
     * @param filter
     * @param type
     * @param count
     */
    public OpponentControlsPermanentCondition(FilterPermanent filter, ComparisonType type, int count) {
        this.filter = filter;
        this.type = type;
        this.count = count;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            FilterPermanent localFilter = filter.copy();
            localFilter.add(new ControllerIdPredicate(opponentId));
            if (ComparisonType.compare(game.getBattlefield().count(localFilter, source.getControllerId(), source, game), type, this.count)) {
                conditionApplies = true;
                break;
            }


        }
        return conditionApplies;
    }

    @Override
    public String toString() {
        return filter.getMessage();
    }
}
