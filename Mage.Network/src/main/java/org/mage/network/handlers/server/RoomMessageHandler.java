package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ServerMessagesMessage;
import org.mage.network.model.ServerMessagesRequest;
import org.mage.network.model.RoomMessage;
import org.mage.network.model.RoomRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class RoomMessageHandler extends SimpleChannelInboundHandler<RoomRequest> {
            
    private final MageServer server;
    
    public RoomMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, RoomRequest msg) {
        ctx.writeAndFlush(new RoomMessage(server.getRoom(msg.getRoomId())));        
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
