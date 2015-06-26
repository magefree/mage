package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class LeaveChatRequest extends ServerRequest {
    
    private UUID chatId;
    
    public LeaveChatRequest(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.leaveChat(chatId, ctx.channel().id().asLongText());
    }
    
    
}
