package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.ClientRegisteredMessage;

/**
 *
 * @author BetaSteward
 */
public class ClientRegisteredMessageHandler extends SimpleChannelInboundHandler<ClientRegisteredMessage> {

    private final MageClient client;
    
    public ClientRegisteredMessageHandler (MageClient client) {
        this.client = client;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ClientRegisteredMessage msg) throws Exception {
        client.clientRegistered(msg.getServerState());
    }
    
}
