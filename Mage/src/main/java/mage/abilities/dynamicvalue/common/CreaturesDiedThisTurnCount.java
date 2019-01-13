
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 * @author LoneFox
 */
public class CreaturesDiedThisTurnCount implements DynamicValue {

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
        return new CreaturesDiedThisTurnCount();
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
