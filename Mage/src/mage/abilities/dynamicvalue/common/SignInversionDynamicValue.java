package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

public class SignInversionDynamicValue implements DynamicValue {
    private DynamicValue value;

    public SignInversionDynamicValue(DynamicValue value) {
        this.value = value.copy();
    }

    SignInversionDynamicValue(final SignInversionDynamicValue dynamicValue) {
        this.value = dynamicValue.value.copy();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return -1 * value.calculate(game, sourceAbility);
    }

    @Override
    public DynamicValue copy() {
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
