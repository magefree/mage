package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.mage.network.Server;

/**
 *
 * @author BetaSteward
 */
public class ConnectionHandler extends ChannelHandlerAdapter {

//    private static final Logger logger = Logger.getLogger(ConnectionHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
        super.channelActive(ctx);
    }

//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        logger.error("Communications error", cause);
//        ctx.close();
//    }
    
}
