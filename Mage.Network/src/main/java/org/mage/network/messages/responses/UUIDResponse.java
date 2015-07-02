package org.mage.network.messages.responses;

import java.util.UUID;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class UUIDResponse extends ClientMessage {
    
    private UUID id;
    
    public UUIDResponse(UUID id) {
        this.id = id;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveId(id);
    }

}