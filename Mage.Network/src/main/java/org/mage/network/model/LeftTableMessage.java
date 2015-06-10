package org.mage.network.model;

import java.io.Serializable;

/**
 *
 * @author BetaSteward
 */
public class LeftTableMessage implements Serializable {
    
    private boolean success;
    
    public LeftTableMessage(boolean success) {
        this.success = success;
    }
    
    public boolean getSuccess() {
        return success;
    }
    
}
