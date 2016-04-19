package mage.interfaces;

import mage.MageException;

/**
 * Light weight action interface.
 * For executing actions without any context.
 *
 * @author ayratn, noxx
 */
public interface Action {

    /**
     * Executes action.
     * @throws mage.MageException
     */
    void execute() throws MageException;
}
