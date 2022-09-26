package mage.remote.messages.callback;

import java.util.UUID;
import mage.view.DeckView;
import mage.remote.handlers.client.ClientMessageHandler;
import mage.remote.messages.ClientMessage;

public class ViewLimitedDeckCallback extends ClientMessage {
    private final UUID tableId;
    private final DeckView deck;
    private final int time;
    private final boolean limited;
    
    public ViewLimitedDeckCallback (UUID tableId, DeckView deck, int time, boolean limited){
        this.tableId = tableId;
        this.deck = deck;
        this.time = time;
        this.limited = limited;
    }
    
    @Override
    public void handleMessage(ClientMessageHandler handler) {
        handler.getClient().viewLimitedDeck(tableId, deck, time, limited);
    }
}
