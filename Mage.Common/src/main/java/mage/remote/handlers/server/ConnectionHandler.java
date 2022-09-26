package mage.remote.handlers.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import mage.remote.ClientCallback;

/**
 *
 * @author BetaSteward
 */
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

//    private static final Logger logger = Logger.getLogger(ConnectionHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientCallback.clients.add(ctx.channel());
        super.channelActive(ctx);
    }

//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        logger.error("Communications error", cause);
//        ctx.close();
//    }
    
}
