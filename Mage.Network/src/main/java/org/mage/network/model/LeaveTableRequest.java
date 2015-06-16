package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class LeaveTableRequest extends ServerRequest {

    private UUID roomId;
    private UUID tableId;

    public LeaveTableRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new LeftTableMessage(server.leaveTable(ctx.channel().id().asLongText(), roomId, tableId))).addListener(WriteListener.getInstance());
    }

}
