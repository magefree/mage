package mage.utils;

import mage.interfaces.ActionWithResult;

/**
 * This class always returns null independently from type <T> used.
 *
 * @author noxx
 */
public abstract class ActionWithNullNegativeResult<T> implements ActionWithResult<T> {
    @Override
    public T negativeResult() {
        return null;
    }
}
