package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class WatchTableRequest extends ServerRequest {
    private final UUID roomId;
    private final UUID tableId;

    public WatchTableRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.watchTable(getSessionId(ctx), roomId, tableId);
    }
    
}
