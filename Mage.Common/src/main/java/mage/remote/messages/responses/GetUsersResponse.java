package mage.remote.messages.responses;

import java.util.List;
import mage.cards.repository.ExpansionInfo;
import mage.view.UserView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class GetUsersResponse extends ClientMessage {

    private final List<UserView> userView;
    public GetUsersResponse(List<UserView> userView) {
        this.userView = userView;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveUserViewList(userView);
    }
}
