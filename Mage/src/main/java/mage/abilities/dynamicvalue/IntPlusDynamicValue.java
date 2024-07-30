
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.util.CardUtil;

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
        if (phrasing == EffectPhrasing.X_HIDDEN){
            return "";
        }
        if (phrasing == EffectPhrasing.FOR_EACH){
            throw new IllegalArgumentException("FOR_EACH phrasing generation is not supported in IntPlusDynamicValue");
        }
        return CardUtil.numberToText(baseValue) + " plus " + value.getMessage(phrasing);
    }

    @Override
    public ValueHint getHint() {
        return value.getHint();
    }

    @Override
    public int getSign() {
        return value.getSign();
    }
}
