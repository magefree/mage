package mage.utils;

import mage.interfaces.ActionWithResult;

/**
 * Used to write less code for ActionWithResult anonymous classes with Boolean return type.
 *
 * @author noxx
 */
public abstract class ActionWithBooleanResult implements ActionWithResult<Boolean> {
    @Override
    public Boolean negativeResult() {
        return false;
    }
}
