package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

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
        server.leaveChat(chatId, getSessionId(ctx));
    }
    
    
}
