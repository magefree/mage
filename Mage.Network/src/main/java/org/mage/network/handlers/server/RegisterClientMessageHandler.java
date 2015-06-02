package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import mage.remote.DisconnectReason;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.RegisterClientMessage;
import org.mage.network.model.ClientRegisteredMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class RegisterClientMessageHandler extends SimpleChannelInboundHandler<RegisterClientMessage> {

    private final MageServer server;
    
    public RegisterClientMessageHandler (MageServer server) {
        this.server = server;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, RegisterClientMessage msg) throws Exception {
        if (!server.registerClient(msg.getUserName(), ctx.channel().id().asLongText(), msg.getMageVersion())) {
            ctx.disconnect();
        }
        else {
            ctx.writeAndFlush(new ClientRegisteredMessage(server.getServerState()));
        }
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.Disconnected);
    }

}
