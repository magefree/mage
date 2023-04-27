package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

public class SignInversionDynamicValue implements DynamicValue {

    private final DynamicValue value;
    private final boolean canBePositive;

    public SignInversionDynamicValue(DynamicValue value) {
        this(value, true);
    }

    public SignInversionDynamicValue(DynamicValue value, boolean canBePositive) {
        this.value = value.copy();
        this.canBePositive = canBePositive;
    }

    SignInversionDynamicValue(final SignInversionDynamicValue dynamicValue) {
        this.value = dynamicValue.value.copy();
        this.canBePositive = dynamicValue.canBePositive;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = value.calculate(game, sourceAbility, effect);
        if (amount >= 0 || canBePositive) {
            return -1 * amount;
        } else {
            return 0;
        }
    }

    @Override
    public SignInversionDynamicValue copy() {
        return new SignInversionDynamicValue(this);
    }

    @Override
    public String toString() {
        return '-' + value.toString();
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }

    @Override
    public int getSign() {
        return -1 * value.getSign();
    }
}
