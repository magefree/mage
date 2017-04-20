package mage.network.protocol.change.compator;

import mage.network.protocol.change.ChangePart;
import mage.network.protocol.change.ChangeType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author magenoxx
 */
public class CompareResult extends HashMap<ChangeType, ChangePart> implements Serializable {

    private boolean isEqual = false;

    public void setEqual(final boolean equal) {
        isEqual = equal;
    }

    public boolean isEqual() {
        return isEqual;
    }
}
