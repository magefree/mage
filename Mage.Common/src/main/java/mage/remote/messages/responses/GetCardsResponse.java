package mage.remote.messages.responses;

import java.util.List;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class GetCardsResponse extends ClientMessage {

    private final List<String> cards;

    public GetCardsResponse(List<String> cards) {
        this.cards = cards;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.receiveStringList(cards);
    }

}
