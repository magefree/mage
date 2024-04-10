package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.constants.ComparisonType;

/**
 * @author notgreat
 */
public class XMVTargetAdjuster extends MVTargetAdjuster {

    public XMVTargetAdjuster() {
        super(ManacostVariableValue.REGULAR, ComparisonType.EQUAL_TO);
    }

    public XMVTargetAdjuster(ComparisonType comparison) {
        super(ManacostVariableValue.REGULAR, comparison);
    }
}
