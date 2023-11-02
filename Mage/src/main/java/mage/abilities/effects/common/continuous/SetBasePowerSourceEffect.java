package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.constants.SubLayer;

/**
 * @author xenohedron
 */
public class SetBasePowerSourceEffect extends SetBasePowerToughnessSourceEffect {

    /**
     * @param amount Power to set as a characteristic-defining ability
     */
    public SetBasePowerSourceEffect(DynamicValue amount) {
        super(amount, null, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
        staticText = "{this}'s power is equal to the number of " + amount.getMessage();
    }

    /**
     * @param amount Power to set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBasePowerSourceEffect(int amount, Duration duration) {
        super(StaticValue.get(amount), null, duration, SubLayer.SetPT_7b);
        staticText = "{this} has base power " + amount + ' ' + duration.toString();
    }

    protected SetBasePowerSourceEffect(final SetBasePowerSourceEffect effect) {
        super(effect);
    }

    @Override
    public SetBasePowerSourceEffect copy() {
        return new SetBasePowerSourceEffect(this);
    }

}
