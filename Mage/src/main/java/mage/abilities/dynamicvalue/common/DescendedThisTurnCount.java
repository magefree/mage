package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.DescendedWatcher;

/**
 * @author TheElk801
 */
public enum DescendedThisTurnCount implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Permanent cards put into your graveyard this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DescendedWatcher.getDescendedCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public DescendedThisTurnCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of times you descended this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}
