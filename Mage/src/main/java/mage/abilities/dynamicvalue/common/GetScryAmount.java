package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;


/**
 * @author notgreat
 */
public enum GetScryAmount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility
                .getEffects()
                .stream()
                .mapToInt(thisEffect -> (Integer) thisEffect.getValue("amount"))
                .findFirst()
                .orElse(0);
    }

    @Override
    public GetScryAmount copy() {
        return GetScryAmount.instance;
    }

    @Override
    public String getMessage() {
        return "card looked at while scrying this way";
    }

    @Override
    public String toString() {
        return "1";
    }
}
