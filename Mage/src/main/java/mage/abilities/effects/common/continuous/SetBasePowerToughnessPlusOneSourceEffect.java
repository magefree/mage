package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.constants.Duration;
import mage.constants.SubLayer;

/**
 * @author xenohedron
 */
public class SetBasePowerToughnessPlusOneSourceEffect extends SetBasePowerToughnessSourceEffect {

    /**
     * @param amount Power to set as a characteristic-defining ability; toughness is that value plus 1
     */
    public SetBasePowerToughnessPlusOneSourceEffect(DynamicValue amount) {
        super(amount, new IntPlusDynamicValue(1, amount), Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
        this.staticText = "{this}'s power is equal to the number of " + amount.getMessage() + " and its toughness is equal to that number plus 1";
    }

    protected SetBasePowerToughnessPlusOneSourceEffect(final SetBasePowerToughnessPlusOneSourceEffect effect) {
        super(effect);
    }

    @Override
    public SetBasePowerToughnessPlusOneSourceEffect copy() {
        return new SetBasePowerToughnessPlusOneSourceEffect(this);
    }

}
