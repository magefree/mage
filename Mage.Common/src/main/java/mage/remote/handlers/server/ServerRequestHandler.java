package mage.remote.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mage.remote.DisconnectReason;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.requests.ServerRequest;

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
    public void channelRead0(ChannelHandlerContext ctx, ServerRequest msg) {
        msg.handleMessage(server, ctx);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.disconnect(getSessionId(ctx), DisconnectReason.Disconnected);
    }
    
    private String getSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }
    
}
