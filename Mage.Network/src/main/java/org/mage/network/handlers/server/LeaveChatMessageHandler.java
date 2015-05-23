package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.JoinChatMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class LeaveChatMessageHandler extends SimpleChannelInboundHandler<JoinChatMessage> {

    private final MageServer server;
    
    public LeaveChatMessageHandler (MageServer server) {
        this.server = server;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, JoinChatMessage msg) throws Exception {
        server.leaveChat(msg.getChatId(), ctx.channel().id().asLongText());
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
