package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.mage.network.Client;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ClientExceptionHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ClientExceptionHandler.class);
    
    private Client client;

    public ClientExceptionHandler(Client client) {
        this.client = client;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Communications error", cause);
        client.disconnect(true);
        //ctx.close();
    }
    
}
