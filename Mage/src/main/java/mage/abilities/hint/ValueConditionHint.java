package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

/**
 * @author notgreat
 */
public class ValueConditionHint implements Hint {

    private final String name;
    private final DynamicValue value;
    private final ConditionHint subHint;

    public ValueConditionHint(String name, DynamicValue value, Condition condition) {
        this(name, value, new ConditionHint(condition, condition.toString()));
    }

    public ValueConditionHint(String name, DynamicValue value, ConditionHint subHint) {
        this.name = name;
        this.value = value;
        this.subHint = subHint;
    }

    private ValueConditionHint(final ValueConditionHint hint) {
        this.name = hint.name;
        this.value = hint.value.copy();
        this.subHint = hint.subHint.copy();
    }

    @Override
    public String getText(Game game, Ability ability) {
        return subHint.getText(game, ability) + " (current: " + value.calculate(game, ability, null) + ")";
    }

    @Override
    public ValueConditionHint copy() {
        return new ValueConditionHint(this);
    }
}
