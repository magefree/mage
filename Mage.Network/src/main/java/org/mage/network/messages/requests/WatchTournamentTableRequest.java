package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class WatchTournamentTableRequest extends ServerRequest {
    private final UUID tableId;

    public WatchTournamentTableRequest(UUID tableId) {
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.watchTournamentTable(getSessionId(ctx), tableId);
    }
    
}
