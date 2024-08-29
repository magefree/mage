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

    // Below are all the phrasings that different Effects employing the DynamicValue interface use in oracle text.
    // The only impact these have on getMessage() is the plurality of the returned value.
    // However, Effects will often need to cater to multiple phrasings to produce oracle text accurate to WOTC's whims.
    enum EffectPhrasing {
        // Plural
        // <do effects> equal to [the number of] <values>
        // i.e. Draw cards equal to the number of cards in your hand.
        // OR, with a modifier:
        // Haunting Apparition’s power is equal to 1 plus the number of green creature cards in the chosen player’s graveyard.
        EQUAL_TO,

        // Plural
        // <do effect with X>, where X is [the number of] <values>
        // i.e. draw X cards, where X is the number of spite counters on Curse of Vengeance.
        // OR, with a modifier:
        // Look at the top X cards of your library, where X is twice the number of lands you control.
        X_IS,

        // Plural
        // Same as X_IS, except the value explanation is skipped (cases where a subsequent effect or other context explains what X is)
        // <do effect with X>
        // i.e. you gain X life
        // getMessage() behavior for this should ALWAYS be to return an empty string
        X_HIDDEN,

        // Singular
        // <do effect> for each <value>
        // i.e. Equipped creature gets +1/+0 for each artifact you control.
        // This phrasing can be used with multipliers, including being negative, but addition and subtraction don't work here.
        // example with multiplier of 3:
        // You gain 3 life for each creature attacking you.
        FOR_EACH
    }

    int calculate(Game game, Ability sourceAbility, Effect effect);

    DynamicValue copy();

    default String getMessage(){
        return getMessage(EffectPhrasing.FOR_EACH);
    }

    /**
     *
     * @return A description of what this Dynamic Value represents.
     * Factor in the Phrasing, which changes the plurality (see EffectPhrasing)
     * If this value represents something that isn't normally phrased like a discreet count, feel free to ignore the phrasing argument.
     * For example:
     *      "the number of creatures you control" or [for each] "creature you control" (discreet count)
     *      vs
     *      "the sacrificed creature's power" (non-discreet)
     */
    default String getMessage(EffectPhrasing phrasing){
        // TODO: this will lose its default status once all DynamicValue implementations have been converted
        return "";
    }

    /**
     *
     * @return A ValueHint with a shortened descriptor of this DynamicValue
     * example:
     *      getMessage(EQUAL_TO) -> the number of card types among cards in your graveyard
     *      getHint() -> ValueHint("Card types in your graveyard", this)
     * Note: make sure to capitalize the ValueHunt name field
     */
    default ValueHint getValueHint(){
        //TODO: this will become getHint() once all DynamicValue implementations have been converted
        return null;
    }

    /**
     *
     * @return A positive value if the result of calculate() is usually positive, and a negative value if it is usually negative.
     */
    default int getSign() {
        return 1;
    }
}
