package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.AbilityResolvedWatcher;

/**
 * @author emerald000
 */
public enum AbilityResolutionCount implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AbilityResolvedWatcher watcher = game.getState().getWatcher(AbilityResolvedWatcher.class);
        if (watcher != null) {
            return watcher.getResolutionCount(game, sourceAbility);
        }
        return 0;
    }

    @Override
    public AbilityResolutionCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "permanents you control";
    }
}
