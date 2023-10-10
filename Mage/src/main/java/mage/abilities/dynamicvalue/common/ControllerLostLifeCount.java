package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 * Amount of life the controller lost this turn.
 *
 * @author Susucr
 */
public enum ControllerLostLifeCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Life lost this turn", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return watcher.getLifeLost(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public ControllerLostLifeCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the amount of life you lost this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}
