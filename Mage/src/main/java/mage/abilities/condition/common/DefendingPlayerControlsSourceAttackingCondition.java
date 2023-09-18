
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;

/**
 * @author Susucr
 */
public class DefendingPlayerControlsSourceAttackingCondition implements Condition {

    private final FilterPermanent filter;

    public DefendingPlayerControlsSourceAttackingCondition(FilterPermanent filter) {
        this.filter = filter.copy();
        this.filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0;
    }
    
}
