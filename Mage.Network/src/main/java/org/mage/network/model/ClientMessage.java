package org.mage.network.model;

import java.io.Serializable;
import org.mage.network.handlers.client.ClientMessageHandler;

/**
 *
 * @author BetaSteward
 */
public abstract class ClientMessage implements Serializable {
        
    public abstract void handleMessage(ClientMessageHandler handler);
    
}
