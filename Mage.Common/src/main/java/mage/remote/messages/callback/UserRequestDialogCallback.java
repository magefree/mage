package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.UserRequestMessage;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class UserRequestDialogCallback extends ClientMessage {
    private final UUID gameId;
    private final UserRequestMessage userRequestMessage;

    public UserRequestDialogCallback(UUID gameId, UserRequestMessage userRequestMessage) {
        this.gameId = gameId;
        this.userRequestMessage = userRequestMessage;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().userRequestDialog(gameId, userRequestMessage);
    }
    
}
