
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.stream.Collectors;

/**
 *
 * @author LevelX2
 */
public class IntPlusDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final int baseValue;

    public IntPlusDynamicValue(int baseValue, DynamicValue value) {
        this.value = value;
        this.baseValue = baseValue;
    }

    IntPlusDynamicValue(final IntPlusDynamicValue dynamicValue) {
        this.value = dynamicValue.value;
        this.baseValue = dynamicValue.baseValue;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return baseValue + value.calculate(game, sourceAbility, effect);
    }

    @Override
    public IntPlusDynamicValue copy() {
        return new IntPlusDynamicValue(this);
    }

    @Override
    public String getMessage(EffectPhrasing phrasing) {
        switch(phrasing){
            case X_HIDDEN:
                return "";
            case FOR_EACH:
                throw new IllegalArgumentException("FOR_EACH phrasing generation is not supported in AdditiveDynamicValue");
            case X_IS:
            case EQUAL_TO:
                return CardUtil.numberToText(baseValue) + " plus " + value.getMessage(phrasing);
            default:
                throw new IllegalArgumentException("enum " + phrasing + " is not supported in AdditiveDynamicValue");
        }
    }

    @Override
    public ValueHint getValueHint() {
        return value.getValueHint();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }
}
