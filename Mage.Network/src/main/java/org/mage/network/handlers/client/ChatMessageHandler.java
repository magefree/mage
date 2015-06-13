package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.ChatMessageMessage;
import org.mage.network.model.ChatMessageRequest;

/**
 *
 * @author BetaSteward
 */
public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessageMessage> {

    private final MageClient client;
    private ChannelHandlerContext ctx;
        
    public ChatMessageHandler (MageClient client) {
        this.client = client;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ChatMessageMessage msg) throws Exception {
        client.receiveChatMessage(msg.getChatId(), msg.getMessage());
    }
    
    public void sendMessage(UUID chatId, String message) {
        ctx.writeAndFlush(new ChatMessageRequest(chatId, message)).addListener(WriteListener.getInstance());
    }
}
