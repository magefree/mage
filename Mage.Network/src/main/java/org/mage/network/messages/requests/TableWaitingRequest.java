package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.TableViewResponse;

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
        ctx.writeAndFlush(new TableViewResponse(server.getTable(roomId, tableId))).addListener(WriteListener.getInstance());
    }
    
}
