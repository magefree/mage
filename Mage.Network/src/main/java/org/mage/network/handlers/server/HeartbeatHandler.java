package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import mage.remote.DisconnectReason;
import org.apache.log4j.Logger;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.PingMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class HeartbeatHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(HeartbeatHandler.class);
    
    private static PingMessage ping = new PingMessage();
    
    private final MageServer server;
    
    public HeartbeatHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                server.disconnect(ctx.channel().id().asLongText(), DisconnectReason.LostConnection);
                ctx.disconnect();
                logger.info("Disconnected due to extended idle");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(ping);
                logger.info("Sending ping");
            }
        }
    }
}
