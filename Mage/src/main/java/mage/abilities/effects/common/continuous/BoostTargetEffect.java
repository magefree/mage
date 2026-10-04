package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;

/**
 * @author notgreat
 */
public class BoostTargetEffect extends BoostGainAbilityGenericEffect {

    public BoostTargetEffect(int power, int toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(power, toughness, duration);
    }

    protected BoostTargetEffect(final BoostTargetEffect effect) {
        super(effect);
    }

    @Override
    public BoostTargetEffect copy() {
        return new BoostTargetEffect(this);
    }

}

