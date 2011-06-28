package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;

public class SignInversionDynamicValue implements DynamicValue {
    private DynamicValue value;

    public SignInversionDynamicValue(DynamicValue value) {
        this.value = value.clone();
    }

    SignInversionDynamicValue(final SignInversionDynamicValue dynamicValue) {
        this.value = dynamicValue.value.clone();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        return -1 * value.calculate(game, sourceAbility);
    }

    @Override
    public DynamicValue clone() {
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
