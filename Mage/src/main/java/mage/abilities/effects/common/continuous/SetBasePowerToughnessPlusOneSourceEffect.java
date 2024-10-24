package mage.abilities.effects.common.continuous;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.ValuePhrasing;

/**
 * @author xenohedron
 */
public class SetBasePowerToughnessPlusOneSourceEffect extends SetBasePowerToughnessSourceEffect {

    /**
     * @param amount Power to set as a characteristic-defining ability; toughness is that value plus 1
     */
    public SetBasePowerToughnessPlusOneSourceEffect(DynamicValue amount) {
        super(amount, new IntPlusDynamicValue(1, amount), Duration.EndOfGame, SubLayer.CharacteristicDefining_7a);
        setStaticText(amount);
    }

    protected SetBasePowerToughnessPlusOneSourceEffect(final SetBasePowerToughnessPlusOneSourceEffect effect) {
        super(effect);
    }

    @Override
    public SetBasePowerToughnessPlusOneSourceEffect copy() {
        return new SetBasePowerToughnessPlusOneSourceEffect(this);
    }

    private void setStaticText(DynamicValue amount) {

        StringBuilder sb = new StringBuilder("{this}'s power is ");

        // Relies on StaticValue messages all being empty
        String message = amount.getMessage(ValuePhrasing.EQUAL_TO);

        if (!message.startsWith("the")) {
            message = "the number of " + message;
        }

        sb.append("equal to ").append(message);
        sb.append(" and its toughness is equal to that number plus 1");

        staticText = sb.toString();
    }

}
