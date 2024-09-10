package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.constants.ComparisonType;

/**
 * @author notgreat
 */
public class XManaValueTargetAdjuster extends ManaValueTargetAdjuster {

    public XManaValueTargetAdjuster() {
        super(GetXValue.instance, ComparisonType.EQUAL_TO);
    }

    public XManaValueTargetAdjuster(ComparisonType comparison) {
        super(GetXValue.instance, comparison);
    }
}
