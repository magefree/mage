package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */

public class NumericSetToEffectValues implements DynamicValue {

    private final String message;
    private final String valueKey;


    public NumericSetToEffectValues(String message, String valueKey) {
        this.message = message;
        this.valueKey = valueKey;
    }
    
    public NumericSetToEffectValues(final NumericSetToEffectValues dynamicValue) {
        super();
        this.message = dynamicValue.message;
        this.valueKey = dynamicValue.valueKey;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Object object = effect.getValue(valueKey);
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return 0;
    }

    @Override
    public NumericSetToEffectValues copy() {
        return new NumericSetToEffectValues(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
