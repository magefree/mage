package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.constants.ValuePhrasing;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class MultipliedValue implements DynamicValue {

    private final DynamicValue value;
    private final int multiplier;

    public MultipliedValue(DynamicValue value, int multiplier) {
        if (value instanceof StaticValue) {
            throw new IllegalArgumentException("Wrong code usage: Don't pass StaticValue into MultipliedValue, just make a StaticValue with the multiplied result");
        }
        this.value = value.copy();
        this.multiplier = multiplier;
    }

    MultipliedValue(final MultipliedValue dynamicValue) {
        this.value = dynamicValue.value.copy();
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.overflowMultiply(value.calculate(game, sourceAbility, effect), multiplier);
    }

    @Override
    public MultipliedValue copy() {
        return new MultipliedValue(this);
    }

    public int getMultiplier(){
        return multiplier;
    }

    public String getMultiplierText(){
        return CardUtil.numberToText(multiplier);
    }

    public String getMultiplierText(String forOne){
        return CardUtil.numberToText(multiplier, forOne);
    }

    @Override
    public String toString() {
        if (value.toString().equals("1")) {
            return Integer.toString(multiplier);
        }
        StringBuilder sb = new StringBuilder();
        if (multiplier == 2) {
            sb.append("twice ");
        } else {
            sb.append(multiplier).append(" * ");
        }
        return sb.append(value).toString();
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }

    @Override
    public String getMessage(ValuePhrasing textPhrasing) {
        switch (textPhrasing) {
            case FOR_EACH:
                return value.getMessage(ValuePhrasing.FOR_EACH);
            case X_HIDDEN:
                return "";
            default:
                StringBuilder sb = new StringBuilder();
                if (multiplier == 2) {
                    sb.append("twice ");
                } else {
                    sb.append(getMultiplierText()).append(" times ");
                }
                return sb.append(value.getMessage(textPhrasing)).toString();
        }

    }

    public ValueHint getValueHint(){
        return value.getValueHint();
    }

    @Override
    public int getSign() {
        return multiplier * value.getSign();
    }
}
