package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.Duration;
import mage.constants.SubLayer;

/**
 * @author xenohedron
 */
public class SetBaseToughnessSourceEffect extends SetBasePowerToughnessSourceEffect {

    /**
     * @param amount Toughness to set as a characteristic-defining ability
     */
    public SetBaseToughnessSourceEffect(DynamicValue amount) {
        super(null, amount, Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
        staticText = "{this}'s toughness is equal to the number of " + amount.getMessage();
    }

    /**
     * @param amount Toughness to set in layer 7b
     * @param duration Duration for the effect
     */
    public SetBaseToughnessSourceEffect(int amount, Duration duration) {
        super(null, StaticValue.get(amount), duration, SubLayer.SetPT_7b);
        staticText = "{this} has base toughness " + amount + ' ' + duration.toString();
    }

    protected SetBaseToughnessSourceEffect(final SetBaseToughnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public SetBaseToughnessSourceEffect copy() {
        return new SetBaseToughnessSourceEffect(this);
    }

}
