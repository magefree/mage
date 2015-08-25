package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author Bill
 */
public class CheatRequest extends ServerRequest {
    private final UUID gameId;
    private final UUID playerId;
    private final DeckCardLists importDeck;

    public CheatRequest(UUID gameId, UUID playerId, DeckCardLists importDeck) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.importDeck = importDeck;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.cheat(gameId, getSessionId(ctx), playerId, importDeck);
    }
    
}
