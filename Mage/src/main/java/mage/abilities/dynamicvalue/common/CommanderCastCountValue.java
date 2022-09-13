package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.CommanderPlaysCountWatcher;

/**
 * @author TheElk801
 */
public enum CommanderCastCountValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Commanders cast from command zone", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        return watcher != null ? watcher.getPlayerCount(sourceAbility.getControllerId()) : 0;
    }

    @Override
    public CommanderCastCountValue copy() {
        return CommanderCastCountValue.instance;
    }

    @Override
    public String toString() {
        return "for each";
    }

    @Override
    public String getMessage() {
        return "time you've cast a commander from the command zone this game";
    }

    public static Hint getHint() {
        return hint;
    }
}
