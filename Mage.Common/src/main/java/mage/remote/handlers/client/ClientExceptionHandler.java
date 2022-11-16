package mage.remote.handlers.client;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import mage.remote.Session;
import org.apache.log4j.Logger;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class ClientExceptionHandler extends ChannelHandlerAdapter {

    private static final Logger logger = Logger.getLogger(ClientExceptionHandler.class);
    
    private Session session;

    public ClientExceptionHandler(Session session) {
        this.session = session;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Communications error", cause);
        session.disconnect(true);
        //ctx.close();
    }
    
}
