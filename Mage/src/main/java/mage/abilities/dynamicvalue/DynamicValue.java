package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ValueHint;
import mage.game.Game;
import mage.util.Copyable;

import java.io.Serializable;

/**
 * Dynamic value can be called multiple times from different places, so don't use inner/changeable fields. If you
 * use it then think x100 times and override Copy method with copy constructor.
 */
public interface DynamicValue extends Serializable, Copyable<DynamicValue> {

    enum Phrasing {
        NUMBER_OF,
        FOR_EACH
    }

    int calculate(Game game, Ability sourceAbility, Effect effect);

    DynamicValue copy();

    default String getMessage(){
        return getMessage(Phrasing.NUMBER_OF);
    }

    /**
     *
     * @return A description of what this Dynamic Value represents.
     * Factor in the Phrasing, which changes the grammar slightly too.
     * If this value represents something that isn't normally phrased like a discreet count, feel free to ignore the phrasing argument.
     * For example:
     *      "the number of creatures you control" or "for each creature you control" (discreet count)
     *      vs
     *      "the sacrificed creature's power" (non-discreet)
     */
    String getMessage(Phrasing phrasing);

    /**
     *
     * @return A ValueHint with a shortened descriptor of this DynamicValue
     * example:
     *      getMessage(NUMBER_OF) -> the number of card types among cards in your graveyard
     *      getHint() -> ValueHint("card types in your graveyard", this)
     */
    ValueHint getHint();

    /**
     *
     * @return A positive value if the result of calculate() is usually positive, and a negative value if it is usually negative.
     */
    default int getSign() {
        return 1;
    }
}
