package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.TimeUnit;
import mage.remote.DisconnectReason;
import org.apache.log4j.Logger;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.PingMessage;
import org.mage.network.messages.PongMessage;

/**
 *
 * @author BetaSteward
 */
public class HeartbeatHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(HeartbeatHandler.class);
    
    private static PingMessage ping = new PingMessage();
    
    private ChannelHandlerContext ctx;
    private long startTime;
    
    private final MageServer server;
    
    public HeartbeatHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                String sessionId = getSessionId(ctx);
                server.disconnect(sessionId, DisconnectReason.LostConnection);
                ctx.disconnect();
                logger.info(sessionId + " disconnected due to extended idle");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                startTime = System.nanoTime();
                ctx.writeAndFlush(ping).addListener(WriteListener.getInstance());
                logger.debug("Sending ping");
            }
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof PongMessage) {
            long milliSeconds = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            server.pingTime(milliSeconds, getSessionId(ctx));
        }
        ctx.fireChannelRead(msg);
    }

    public void pingClient() {
        startTime = System.nanoTime();
        ctx.writeAndFlush(ping).addListener(WriteListener.getInstance());
    }
    
    private String getSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

}
