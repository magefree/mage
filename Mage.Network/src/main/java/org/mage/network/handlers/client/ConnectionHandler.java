package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.MessageType;

/**
 *
 * @author BetaSteward
 */
public class ConnectionHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ConnectionHandler.class);
    private final MageClient client;
        
    public ConnectionHandler (MageClient client) {
        this.client = client;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Connection error", cause);
        client.disconnected(true);
    }
    
}