package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.watchers.common.SpellsCastNotFromHandWatcher;

/**
 * @author TheElk801
 */
public enum SpellsCastNotFromHandValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Spells you've cast this turn from anywhere other than your hand", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return SpellsCastNotFromHandWatcher.getCount(sourceAbility.getControllerId(), game);
    }

    @Override
    public SpellsCastNotFromHandValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn from anywhere other than your hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
