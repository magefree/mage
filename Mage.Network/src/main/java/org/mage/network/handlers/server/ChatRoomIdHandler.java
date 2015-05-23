package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ChatRoomIdMessage;
import org.mage.network.model.ChatRoomIdRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ChatRoomIdHandler extends SimpleChannelInboundHandler<ChatRoomIdRequest> {
            
    private final MageServer server;
    
    public ChatRoomIdHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ChatRoomIdRequest msg) {
        ctx.writeAndFlush(new ChatRoomIdMessage(server.getRoomChatId(msg.getId())));        
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
