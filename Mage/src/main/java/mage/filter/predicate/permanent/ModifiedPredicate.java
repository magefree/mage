package mage.filter.predicate.permanent;

import mage.constants.SubType;
import mage.counters.Counter;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public enum ModifiedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .anyMatch(x -> x > 0)
                || input
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.EQUIPMENT, game)
                        || (permanent.hasSubtype(SubType.AURA, game)
                        && permanent.isControlledBy(input.getControllerId())));
    }

    @Override
    public String toString() {
        return "modified";
    }
}
