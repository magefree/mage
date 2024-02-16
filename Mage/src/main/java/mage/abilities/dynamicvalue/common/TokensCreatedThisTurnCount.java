package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.CreatedTokenWatcher;

/**
 * @author TheElk801
 */
public enum TokensCreatedThisTurnCount implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("The number of tokens you created this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CreatedTokenWatcher.getPlayerCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public TokensCreatedThisTurnCount copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the number of tokens you created this turn";
    }

    @Override
    public String toString() {
        return "X";
    }
}
