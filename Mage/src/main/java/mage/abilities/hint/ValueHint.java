package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class ValueHint implements Hint {

    private final String name;
    private final DynamicValue value;

    public ValueHint(String name, DynamicValue value) {
        this.name = name;
        this.value = value;
    }

    private ValueHint(final ValueHint hint) {
        this.name = hint.name;
        this.value = hint.value.copy();
    }

    @Override
    public String getText(Game game, Ability ability) {
        return name + ": " + value.calculate(game, ability, null);
    }

    @Override
    public ValueHint copy() {
        return new ValueHint(this);
    }
}
