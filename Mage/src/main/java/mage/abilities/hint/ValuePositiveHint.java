package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

/**
 * @author Susucr
 */
public class ValuePositiveHint implements Hint {

    private final String name;
    private final DynamicValue value;

    public ValuePositiveHint(String name, DynamicValue value) {
        this.name = name;
        this.value = value;
    }

    private ValuePositiveHint(final ValuePositiveHint hint) {
        this.name = hint.name;
        this.value = hint.value.copy();
    }

    @Override
    public String getText(Game game, Ability ability) {
        int amount = value.calculate(game, ability, null);
        return amount <= 0 ? "" : name + ": " + amount;
    }

    @Override
    public ValuePositiveHint copy() {
        return new ValuePositiveHint(this);
    }
}
