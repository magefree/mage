package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.constants.ComparisonType;

/**
 *
 * @author notgreat
 */
public class XMVAdjuster extends DynamicValueMVTargetsAdjuster {

    public XMVAdjuster() {
        super(ManacostVariableValue.REGULAR, ComparisonType.EQUAL_TO);
    }
    public XMVAdjuster(ComparisonType comparison) {
        super(ManacostVariableValue.REGULAR, comparison);
    }
}
