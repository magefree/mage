package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.target.targetpointer.SourceTargetPointer;
import mage.target.targetpointer.TargetPointer;

/**
 * @author notgreat
 */
public class BoostSourceEffect extends BoostGainAbilityGenericEffect {
    public BoostSourceEffect(int power, int toughness, Duration duration) {
        this(power, toughness, duration, null);
    }

    public BoostSourceEffect(int power, int toughness, Duration duration, String description) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, description);
    }
    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, null);
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, String description) {
        super(power, toughness, duration);
        TargetPointer targetPointer = new SourceTargetPointer();
        if (description != null) {
            targetPointer.setTargetDescription(description);
        }
        this.setTargetPointer(targetPointer);
    }

    protected BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

}
