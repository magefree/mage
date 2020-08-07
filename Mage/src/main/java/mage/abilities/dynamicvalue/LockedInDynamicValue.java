
package mage.abilities.dynamicvalue;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 * The first calculated value is used as long as the class instance is in use
 *
 * IMPORTANT: If used the ability / effect that uses a locked in dynamic value
 * has to really copy the dnamic value in its copy method (not reference)
 *
 * @author LevelX2
 */
public class LockedInDynamicValue implements DynamicValue {

    private boolean valueChecked = false;
    private int lockedInValue;
    private final DynamicValue basicDynamicValue;

    public LockedInDynamicValue(DynamicValue dynamicValue) {
        this.basicDynamicValue = dynamicValue;
    }

    public LockedInDynamicValue(LockedInDynamicValue dynamicValue, final boolean copy) {
        this.basicDynamicValue = dynamicValue.basicDynamicValue;
        this.lockedInValue = dynamicValue.lockedInValue;
        this.valueChecked = dynamicValue.valueChecked;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (!valueChecked) {
            lockedInValue = basicDynamicValue.calculate(game, sourceAbility, effect);
            valueChecked = true;
        }
        return lockedInValue;
    }

    @Override
    public LockedInDynamicValue copy() {
        return new LockedInDynamicValue(this, true);
    }

    @Override
    public String getMessage() {
        return basicDynamicValue.getMessage();
    }

}
