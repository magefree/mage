package mage.remote.messages.responses;

import java.util.UUID;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

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