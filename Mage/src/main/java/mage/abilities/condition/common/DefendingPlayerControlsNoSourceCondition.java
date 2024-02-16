
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsNoSourcePredicate;
import mage.game.Game;

/**
 * This condition always returns false outside of the combat phase.
 *
 * The name is bad, but "No Source" is refering to the fact this is to be used outside
 * a "Whenever ~ attacks," or similar, where the "defending player" takes another
 * meaning. {@link DefendingPlayerControlsNoSourcePredicate} for more info.
 *
 * @author Susucr
 */
public class DefendingPlayerControlsNoSourceCondition implements Condition {

    private final FilterPermanent filter;

    public DefendingPlayerControlsNoSourceCondition(FilterPermanent filter) {
        this.filter = filter.copy();
        this.filter.add(DefendingPlayerControlsNoSourcePredicate.instance);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0;
    }

}
