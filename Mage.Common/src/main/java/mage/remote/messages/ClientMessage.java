package mage.remote.messages;

import java.io.Serializable;
import mage.remote.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public abstract class ClientMessage implements Serializable {
        
    public abstract void handleMessage(ClientMessageHandler handler);
    
}
