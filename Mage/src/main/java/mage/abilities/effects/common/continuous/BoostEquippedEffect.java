package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.target.targetpointer.SourceAttachedTargetPointer;

/**
 * @author notgreat
 */
public class BoostEquippedEffect extends BoostGainAbilityGenericEffect {

    public BoostEquippedEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEquippedEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostEquippedEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEquippedEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(power, toughness, duration);
        this.setTargetPointer(new SourceAttachedTargetPointer("equipped creature"));
    }

    protected BoostEquippedEffect(final BoostEquippedEffect effect) {
        super(effect);
    }

    @Override
    public BoostEquippedEffect copy() {
        return new BoostEquippedEffect(this);
    }

}
