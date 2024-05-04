package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;

/**
 * @author notgreat
 */
public class XTargetsCountAdjuster extends TargetsCountAdjuster {

    public XTargetsCountAdjuster() {
        super(ManacostVariableValue.REGULAR);
    }
}
