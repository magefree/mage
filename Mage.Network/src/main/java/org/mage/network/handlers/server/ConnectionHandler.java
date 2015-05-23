package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.mage.network.Server;

/**
 *
 * @author BetaSteward
 */
public class ConnectionHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
        super.channelActive(ctx);
    }
    
}
