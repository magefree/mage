package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.ServerMessagesResponse;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesRequest extends ServerRequest {
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new ServerMessagesResponse(server.getServerMessages(getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }
    
    
}
