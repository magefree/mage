package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.DealtDamageThisGameWatcher;

/**
 * requires DealtDamageThisGameWatcher
 *
 * @author TheElk801
 */
public enum SourceHasntDealtDamageThisGameCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "This creature hasn't dealt damage yet this game"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return DealtDamageThisGameWatcher.checkCreature(game, source);
    }

    @Override
    public String toString() {
        return "{this} hasn't dealt damage yet";
    }
}
