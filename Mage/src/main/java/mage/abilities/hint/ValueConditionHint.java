package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

/**
 * @author notgreat
 */
public class ValueConditionHint extends ConditionHint {

    private final DynamicValue value;

    public ValueConditionHint(DynamicValue value, Condition condition) {
        this(condition.toString(), value, condition);
    }

    public ValueConditionHint(String name, DynamicValue value, Condition condition) {
        super(condition, name);
        this.value = value;
    }

    private ValueConditionHint(final ValueConditionHint hint) {
        super(hint);
        this.value = hint.value.copy();
    }

    @Override
    public String getText(Game game, Ability ability) {
        return super.getText(game, ability) + " (current: " + value.calculate(game, ability, null) + ")";
    }

    @Override
    public ValueConditionHint copy() {
        return new ValueConditionHint(this);
    }
}
