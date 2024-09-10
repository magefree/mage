
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;

/**
 * @author Susucr
 */
public enum PermanentsSacrificedThisTurnCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PermanentsSacrificedWatcher watcher = game.getState().getWatcher(PermanentsSacrificedWatcher.class);
        if (watcher != null) {
            return watcher.getThisTurnSacrificedPermanents();
        }
        return 0;
    }

    @Override
    public PermanentsSacrificedThisTurnCount copy() {
        return PermanentsSacrificedThisTurnCount.instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "permanents sacrificed this turn";
    }

}
