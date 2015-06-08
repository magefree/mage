package org.mage.network.model;

import java.io.Serializable;

/**
 *
 * @author BetaSteward
 */
public class JoinTableMessage implements Serializable {
    
    private boolean success;
    
    public JoinTableMessage(boolean success) {
        this.success = success;
    }
    
    public boolean getSuccess() {
        return success;
    }
    
}
