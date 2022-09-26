package mage.remote.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import mage.remote.messages.PingMessage;
import mage.remote.messages.PongMessage;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class PingMessageHandler extends SimpleChannelInboundHandler<PingMessage> {

    private static final Logger logger = Logger.getLogger(PingMessageHandler.class);
    private static PongMessage pong = new PongMessage();
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        ctx.writeAndFlush(pong).addListener(WriteListener.getInstance());
//        logger.info("Received ping.  Sending pong");
    }
    
}
