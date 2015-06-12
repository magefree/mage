package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.InetSocketAddress;
import mage.interfaces.ServerState;
import mage.remote.DisconnectReason;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.RegisterClientRequest;
import org.mage.network.model.ClientRegisteredMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class RegisterClientMessageHandler extends SimpleChannelInboundHandler<RegisterClientRequest> {

    private final MageServer server;
    
    public RegisterClientMessageHandler (MageServer server) {
        this.server = server;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, RegisterClientRequest msg) throws Exception {
        String host = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
        boolean result = server.registerClient(msg.getConnection(), ctx.channel().id().asLongText(), msg.getMageVersion(), host);
        if (result) {
            ctx.writeAndFlush(new ClientRegisteredMessage(server.getServerState()));
        }
        else {
            ctx.writeAndFlush(new ClientRegisteredMessage(new ServerState()));
            server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.ValidationError);
        }
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.Disconnected);
    }

}
