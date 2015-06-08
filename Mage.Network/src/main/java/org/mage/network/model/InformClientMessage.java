package org.mage.network.model;

import java.io.Serializable;

/**
 *
 * @author BetaSteward
 */
public class InformClientMessage implements Serializable {
    
    private String title;
    private String message;
    private MessageType type;
    
    public InformClientMessage(String title, String message, MessageType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
        
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
    
    public MessageType getType() {
        return type;
    }
}
