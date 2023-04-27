package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

public enum SourceHasCountersCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        return permanent
                .getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .anyMatch(x -> x > 0);
    }
}
