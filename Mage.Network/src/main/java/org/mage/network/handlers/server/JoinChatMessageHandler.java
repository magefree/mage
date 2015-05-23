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
public class JoinChatMessageHandler extends SimpleChannelInboundHandler<JoinChatMessage> {

    private final MageServer server;
    
    public JoinChatMessageHandler (MageServer server) {
        this.server = server;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, JoinChatMessage msg) throws Exception {
        server.joinChat(msg.getChatId(), ctx.channel().id().asLongText());
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
