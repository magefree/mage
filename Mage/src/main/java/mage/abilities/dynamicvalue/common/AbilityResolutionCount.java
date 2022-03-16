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
        return AbilityResolvedWatcher.getResolutionCount(game, sourceAbility);
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
