package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.TableViewResponse;

/**
 *
 * @author BetaSteward
 */
public class TableWaitingRequest extends ServerRequest {

    private UUID roomId;
    private UUID tableId;
    
    public TableWaitingRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TableViewResponse(server.getTable(getSessionId(ctx), roomId, tableId))).addListener(WriteListener.getInstance());
    }
    
}
