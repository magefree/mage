package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.mage.network.handlers.WriteListener;
import org.mage.network.messages.PingMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class HeartbeatHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(HeartbeatHandler.class);
    
    private static PingMessage ping = new PingMessage();
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.channel().close();
                logger.info("Disconnected due to extended idle");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(ping).addListener(WriteListener.getInstance());
                logger.debug("Sending ping");
            }
        }
    }
}
