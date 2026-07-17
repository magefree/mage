package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.filter.StaticFilters;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum YouControlALegendaryCreatureCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY, source.getControllerId(), source, game, 1);
    }

    @Override
    public String toString() {
        return "you control a legendary creature";
    }
}
