package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 * @author Susucr
 */
public enum CreaturesThatAttackedThisTurnCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Creatures that attacked this turn", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AttackedThisTurnWatcher watcher = game.getState()
                .getWatcher(AttackedThisTurnWatcher.class);

        return watcher == null ? 0 : watcher.getAttackedThisTurnCreatures().size();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "creatures that attacked this turn";
    }

    public static Hint getHint() {
        return hint;
    }
}
