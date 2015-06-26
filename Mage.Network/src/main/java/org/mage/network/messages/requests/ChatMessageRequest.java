package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class ChatMessageRequest extends ServerRequest {
    
    private UUID chatId;
    private String message;
    
    public ChatMessageRequest(UUID chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.receiveChatMessage(chatId, ctx.channel().id().asLongText(), message);
    }
            
}
