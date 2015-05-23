package org.mage.network.model;

import java.io.Serializable;

/**
 *
 * @author BetaSteward
 */
public class InformClientMessage implements Serializable {
    
    private String message;
    private MessageType type;
    
    public InformClientMessage(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }
        
    public String getMessage() {
        return message;
    }
    
    public MessageType getType() {
        return type;
    }
}
