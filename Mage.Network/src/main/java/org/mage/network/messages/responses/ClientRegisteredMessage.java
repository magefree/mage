package org.mage.network.messages.responses;

import java.io.Serializable;
import mage.interfaces.ServerState;

/**
 *
 * @author BetaSteward
 */
public class ClientRegisteredMessage implements Serializable {
    
    private ServerState state;
    
    public ClientRegisteredMessage(ServerState state) {
        this.state = state;
    }
    
    public ServerState getServerState() {
        return state;
    }
}
