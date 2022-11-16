package mage.remote.messages.responses;

import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.remote.messages.MessageType;

/**
 *
 * @author BetaSteward
 */
public class UserRegisteredResponse extends ClientMessage {
    
    private final String message;
    
    public UserRegisteredResponse(String message) {
        this.message = message;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        if(message!=null){
            handler.getClient().inform("Registration Error", message, MessageType.ERROR);
            handler.receiveBoolean(false);
        } else {
            handler.receiveBoolean(true);
        }
    }
}
