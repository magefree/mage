package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.watchers.common.CommanderPlaysCountWatcher;

/**
 * @author JayDi85
 */
public class CommanderPlaysCount implements DynamicValue {

    private Integer multiplier;

    public CommanderPlaysCount() {
        this(1);
    }

    public CommanderPlaysCount(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public CommanderPlaysCount(final CommanderPlaysCount dynamicValue) {
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
        int value = 0;
        if (watcher != null) {
            value = watcher.getPlaysCount(sourceAbility.getSourceId());
        }
        return value * multiplier;
    }

    @Override
    public CommanderPlaysCount copy() {
        return new CommanderPlaysCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
