package org.mage.network.messages.callback;

import java.util.UUID;
import mage.view.DeckView;
import org.mage.network.handlers.client.ClientMessageHandler;
import org.mage.network.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class ConstructCallback extends ClientMessage {
    private final UUID tableId;
    private final DeckView deck;
    private final int time;

    public ConstructCallback(UUID tableId, DeckView deck, int time) {
        this.tableId = tableId;
        this.deck = deck;
        this.time = time;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().construct(tableId, deck, time);
    }
    
}
