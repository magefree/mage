package org.mage.network.handlers;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward
 */
public class WriteListener implements ChannelFutureListener {

    private static final Logger logger = Logger.getLogger(WriteListener.class);
    
    private static final WriteListener instance = new WriteListener();
    
    public static WriteListener getInstance() {
        return instance;
    }
    
    private WriteListener() { }
            
    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            logger.error("Communication error", future.cause());
        }
    }
    
}
