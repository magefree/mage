package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ServerMessagesMessage;
import org.mage.network.model.ServerMessagesRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ServerMessageHandler extends SimpleChannelInboundHandler<ServerMessagesRequest> {
            
    private final MageServer server;
    
    public ServerMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ServerMessagesRequest msg) {
        ctx.writeAndFlush(new ServerMessagesMessage(server.getServerMessages()));        
    }
    
}
