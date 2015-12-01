package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;

public class SignInversionDynamicValue implements DynamicValue {

    private final DynamicValue value;

    public SignInversionDynamicValue(DynamicValue value) {
        this.value = value.copy();
    }

    SignInversionDynamicValue(final SignInversionDynamicValue dynamicValue) {
        this.value = dynamicValue.value.copy();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return -1 * value.calculate(game, sourceAbility, effect);
    }

    @Override
    public SignInversionDynamicValue copy() {
        return new SignInversionDynamicValue(this);
    }

    @Override
    public String toString() {
        return "-" + value.toString();
    }

    @Override
    public String getMessage() {
        return value.getMessage();
    }
}
