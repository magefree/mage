package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.DeckView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

/**
 *
 * @author BetaSteward
 */
public class SideboardCallback extends ClientMessage {
    private final UUID tableId;
    private final DeckView deck;
    private final int time;
    private final boolean limited;

    public SideboardCallback(UUID tableId, DeckView deck, int time, boolean limited) {
        this.tableId = tableId;
        this.deck = deck;
        this.time = time;
        this.limited = limited;
    }

    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().sideboard(tableId, deck, time, limited);
    }
    
}
