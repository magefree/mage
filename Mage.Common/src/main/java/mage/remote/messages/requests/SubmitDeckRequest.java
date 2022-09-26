package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class SubmitDeckRequest extends ServerRequest {
    private final UUID tableId;
    private final DeckCardLists deckCardLists;

    public SubmitDeckRequest(UUID tableId, DeckCardLists deckCardLists) {
        this.tableId = tableId;
        this.deckCardLists = deckCardLists;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new BooleanResponse(server.submitDeck(getSessionId(ctx), tableId, deckCardLists))).addListener(WriteListener.getInstance());
    }
    
}
