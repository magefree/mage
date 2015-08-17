package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import mage.remote.DisconnectReason;
import org.apache.log4j.Logger;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ServerExceptionHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ServerExceptionHandler.class);
    
    private MageServer server;

    public ServerExceptionHandler(MageServer server) {
        this.server = server;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Communications error", cause);
        server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.Undefined);
        //ctx.close();
    }
    
}
