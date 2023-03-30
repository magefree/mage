package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;

/**
 * @author TheElk801
 */

public enum CorruptedCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "An opponent has three or more poison counters");

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getCounters)
                .anyMatch(counters -> counters.getCount(CounterType.POISON) >= 3);
    }

    @Override
    public String toString() {
        return "an opponent has three or more poison counters";
    }

    public static Hint getHint() {
        return hint;
    }
}
