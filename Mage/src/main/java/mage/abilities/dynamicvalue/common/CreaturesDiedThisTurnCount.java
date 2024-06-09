
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 * @author LoneFox
 */
public enum CreaturesDiedThisTurnCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Number of creatures that died this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurn();
        }
        return 0;
    }

    @Override
    public CreaturesDiedThisTurnCount copy() {
        return CreaturesDiedThisTurnCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "creature that died this turn";
    }

}
