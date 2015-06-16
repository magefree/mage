package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mage.remote.DisconnectReason;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.ServerRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ServerRequestHandler extends SimpleChannelInboundHandler<ServerRequest> {
            
    private final MageServer server;
    
    public ServerRequestHandler(MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, ServerRequest msg) {
        msg.handleMessage(server, ctx);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.Disconnected);
    }
    
}
