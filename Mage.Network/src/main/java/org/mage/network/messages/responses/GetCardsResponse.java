package org.mage.network.messages.responses;

import java.util.List;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

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
