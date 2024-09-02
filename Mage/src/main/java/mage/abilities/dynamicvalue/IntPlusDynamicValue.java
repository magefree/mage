
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.constants.ValuePhrasing;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class IntPlusDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final int baseValue;
    private final boolean useWord;

    public IntPlusDynamicValue(int baseValue, DynamicValue value) {
        this(baseValue, value, false);
    }

    public IntPlusDynamicValue(int baseValue, DynamicValue value, boolean useWord) {
        this.value = value;
        this.baseValue = baseValue;
        this.useWord = useWord;
    }

    IntPlusDynamicValue(final IntPlusDynamicValue dynamicValue) {
        this.value = dynamicValue.value;
        this.baseValue = dynamicValue.baseValue;
        this.useWord = dynamicValue.useWord;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return baseValue + value.calculate(game, sourceAbility, effect);
    }

    @Override
    public IntPlusDynamicValue copy() {
        return new IntPlusDynamicValue(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(baseValue).append(" plus ");
        return sb.append(value.toString()).toString();
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }

    @Override
    public String getMessage(ValuePhrasing textPhrasing) {
        switch(textPhrasing){
            case X_HIDDEN:
                return "";
            case FOR_EACH:
                throw new IllegalArgumentException("FOR_EACH phrasing generation is not supported in IntPlusDynamicValue");
            default:
                return (useWord ? CardUtil.numberToText(baseValue) : baseValue) + " plus " + value.getMessage(textPhrasing);
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
