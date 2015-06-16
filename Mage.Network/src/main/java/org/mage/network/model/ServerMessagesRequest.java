package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class ServerMessagesRequest extends ServerRequest {

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new ServerMessagesMessage(server.getServerMessages())).addListener(WriteListener.getInstance());
    }
    
    
}
