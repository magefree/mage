package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.util.Copyable;

import java.io.Serializable;

/**
 * Dynamic value can be called multiple times from different places, so don't use inner/changeable fields. If you
 * use it then think x100 times and override Copy method with copy constructor.
 */
public interface DynamicValue extends Serializable, Copyable<DynamicValue> {

    int calculate(Game game, Ability sourceAbility, Effect effect);

    DynamicValue copy();

    String getMessage();

    /**
     *
     * @return A positive value if the result of calculate() is usually positive, and a negative value if it is usually negative.
     */
    default int getSign() {
        return 1;
    }
}
