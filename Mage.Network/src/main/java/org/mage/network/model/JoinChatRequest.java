package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class JoinChatRequest extends ServerRequest {
    
    private UUID chatId;
    
    public JoinChatRequest(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.joinChat(chatId, ctx.channel().id().asLongText());
    }
    
}
