package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.constants.ComparisonType;

/**
 * @author notgreat
 */
public class XManaValueTargetAdjuster extends ManaValueTargetAdjuster {

    public XManaValueTargetAdjuster() {
        super(ManacostVariableValue.REGULAR, ComparisonType.EQUAL_TO);
    }

    public XManaValueTargetAdjuster(ComparisonType comparison) {
        super(ManacostVariableValue.REGULAR, comparison);
    }
}
