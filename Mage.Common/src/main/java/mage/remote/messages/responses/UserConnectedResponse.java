
package mage.remote.messages.responses;

import java.util.Optional;
import mage.interfaces.ServerState;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;
import mage.remote.messages.MessageType;

public class UserConnectedResponse extends ClientMessage {
    private final ServerState state;
    private final String message;
    
    public UserConnectedResponse(ServerState state, String message) {
        this.state = state;
        this.message = message;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        if(message!=null){
            handler.getClient().inform("Registration Error", message, MessageType.ERROR);
            handler.receiveServerState(Optional.empty());
        } else {
            handler.receiveServerState(Optional.of(state));
        }
    }
}
