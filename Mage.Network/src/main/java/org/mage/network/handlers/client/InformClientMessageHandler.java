package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.InformClientMessage;

/**
 *
 * @author BetaSteward
 */
public class InformClientMessageHandler extends SimpleChannelInboundHandler<InformClientMessage> {

    private final MageClient client;
    
    public InformClientMessageHandler (MageClient client) {
        this.client = client;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, InformClientMessage msg) throws Exception {
        client.inform(msg.getMessage(), msg.getType());
    }
    
}
