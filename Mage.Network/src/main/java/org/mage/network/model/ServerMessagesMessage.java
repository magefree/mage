package org.mage.network.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesMessage implements Serializable {
    
    private List<String> messages;
    
    public ServerMessagesMessage(List<String> messages) {
        this.messages = messages;
    }
    
    public List<String> getMessages() {
        return messages;
    }
    
}
