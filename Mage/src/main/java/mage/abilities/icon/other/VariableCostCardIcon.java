package mage.abilities.icon.other;

import mage.abilities.icon.CardIconImpl;
import mage.abilities.icon.CardIconType;

/**
 * Showing x cost value
 *
 * @author JayDi85
 */
public class VariableCostCardIcon extends CardIconImpl {

    public VariableCostCardIcon(int costX) {
        super(CardIconType.OTHER_COST_X, "Announced X = " + costX, "x=" + costX);
    }
}
