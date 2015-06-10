package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.JoinTableMessage;
import org.mage.network.model.JoinTableRequest;
import org.mage.network.model.JoinedTableMessage;
import org.mage.network.model.LeaveTableRequest;
import org.mage.network.model.LeftTableMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class LeaveTableMessageHandler extends SimpleChannelInboundHandler<LeaveTableRequest> {
            
    private final MageServer server;
    
    public LeaveTableMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, LeaveTableRequest msg) {
        LeftTableMessage resp = new LeftTableMessage(server.leaveTable(ctx.channel().id().asLongText(), msg.getRoomId(), msg.getTableId()));
        ctx.writeAndFlush(resp);        
    }

}
