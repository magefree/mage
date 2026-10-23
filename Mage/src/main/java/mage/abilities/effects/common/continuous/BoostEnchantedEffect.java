package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.target.targetpointer.SourceAttachedTargetPointer;

/**
 * @author notgreat
 */
public class BoostEnchantedEffect extends BoostGainAbilityGenericEffect {

    public BoostEnchantedEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(power, toughness, duration);
        this.setTargetPointer(new SourceAttachedTargetPointer("enchanted creature"));
    }

    protected BoostEnchantedEffect(final BoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public BoostEnchantedEffect copy() {
        return new BoostEnchantedEffect(this);
    }

}
