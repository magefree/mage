package mage.utils;

import mage.interfaces.ActionWithResult;

/**
 * @author noxx
 */
public abstract class ActionWithBooleanResult implements ActionWithResult<Boolean> {
    @Override
    public Boolean negativeResult() {
        return false;
    }
}
