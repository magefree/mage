package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class RemoveTableRequest extends ServerRequest {
    
    private UUID roomId;
    private UUID tableId;
    
    public RemoveTableRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.removeTable(ctx.channel().id().asLongText(), roomId, tableId);
    }
            
}
