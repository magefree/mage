package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.SendChatMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ChatMessageHandler extends SimpleChannelInboundHandler<SendChatMessage> {

    private final MageServer server;
    
    public ChatMessageHandler (MageServer server) {
        this.server = server;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, SendChatMessage msg) throws Exception {
        server.receiveChatMessage(msg.getChatId(), ctx.channel().id().asLongText(), msg.getMessage());
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
