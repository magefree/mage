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
public abstract class DynamicValue implements Serializable, Copyable<DynamicValue> {

    public enum Phrasing {
        NUMBER_OF,
        FOR_EACH
    }

    private static String[] numberWords = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"
    };

    protected int multiplier;

    public DynamicValue() {
        this(1);
    }

    public DynamicValue(int multiplier) {
        this.multiplier = multiplier;
    }

    public int calculate(Game game, Ability sourceAbility, Effect effect){
        return multiplier * calculateBase(game, sourceAbility, effect);
    }

    /**
     *
     * @return A calculation of this value with the current game state, before the multiplier is applied.
     */
    public abstract int calculateBase(Game game, Ability sourceAbility, Effect effect);

    protected DynamicValue(final DynamicValue other) {
        this.multiplier = other.multiplier;
    }

    //TODO: find all usages of implementations of toString

    public String getMessage(){
        return getMessage(Phrasing.NUMBER_OF, true);
    }

    public String getMessage(Phrasing phrasing){
        return getMessage(phrasing, true);
    }

    /**
     *
     * @return A description of what this Dynamic Value represents.
     * Factor in the Phrasing, which changes the grammar slightly too.
     * If this value represents something that isnt normally phrased like a discreet count, feel free to ignore the phrasing argument.
     * For example:
     *      "the number of creatures you control" or "for each creature you control" (discreet count) vs
     *      "the sacrificed creature's power" (non-discreet)
     */
    public abstract String getMessage(Phrasing phrasing, boolean useMultiplier);

    public int getMultiplier(){
        return multiplier;
    }

    public String getMultiplierAsWord(){
        if (multiplier >= numberWords.length){
            return Integer.toString(multiplier);
        }
        return numberWords[multiplier];
    }

    /**
     *
     * @return A positive value if the result of calculate() is usually positive, and a negative value if it is usually negative.
     */
    public int getSign() {
        return getMultiplier();
    }
}
