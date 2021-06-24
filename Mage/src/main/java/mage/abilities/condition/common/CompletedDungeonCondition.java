package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum CompletedDungeonCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance,"You've completed a dungeon");

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: implement
        return false;
    }

    @Override
    public String toString() {
        return "you've completed a dungeon";
    }

    public static Hint getHint() {
        return hint;
    }
}
