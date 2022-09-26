package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class UpdateDeckRequest extends ServerRequest {
    private final UUID tableId;
    private final DeckCardLists deckCardLists;

    public UpdateDeckRequest(UUID tableId, DeckCardLists deckCardLists) {
        this.tableId = tableId;
        this.deckCardLists = deckCardLists;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.updateDeck(getSessionId(ctx), tableId, deckCardLists);
    }
    
}
