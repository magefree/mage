package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.GetXValue;

/**
 * @author notgreat
 */
public class XTargetsCountAdjuster extends TargetsCountAdjuster {

    public XTargetsCountAdjuster() {
        super(GetXValue.instance);
    }
}
