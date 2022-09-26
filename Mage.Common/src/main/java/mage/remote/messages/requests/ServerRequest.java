package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.io.Serializable;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public abstract class ServerRequest implements Serializable {
        
    public abstract void handleMessage(MageServer server, ChannelHandlerContext ctx);
    
    protected String getSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

}
