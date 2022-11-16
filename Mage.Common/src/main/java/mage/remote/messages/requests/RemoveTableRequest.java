package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class RemoveTableRequest extends ServerRequest {
    
    private UUID tableId;
    
    public RemoveTableRequest(UUID tableId) {
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.removeTable(getSessionId(ctx), tableId);
    }
            
}
