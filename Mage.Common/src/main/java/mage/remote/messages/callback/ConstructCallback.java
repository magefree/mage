package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.DeckView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

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
