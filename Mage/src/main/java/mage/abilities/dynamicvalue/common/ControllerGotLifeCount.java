package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * Amount of life the controller got this turn.
 *
 * @author LevelX2
 */
public enum ControllerGotLifeCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Life gained this turn", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return this.calculate(game, sourceAbility.getControllerId());
    }

    public int calculate(Game game, UUID controllerId) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (watcher != null) {
            return watcher.getLifeGained(controllerId);
        }
        return 0;
    }

    @Override
    public ControllerGotLifeCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the amount of life you've gained this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}
