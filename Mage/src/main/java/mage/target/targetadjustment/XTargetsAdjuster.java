package mage.target.targetadjustment;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;

/**
 * @author notgreat
 */
public class XTargetsAdjuster extends DynamicValueTargetsAdjuster {

    public XTargetsAdjuster() {
        super(ManacostVariableValue.REGULAR);
    }
}
