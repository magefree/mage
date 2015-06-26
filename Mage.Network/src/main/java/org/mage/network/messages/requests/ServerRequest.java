package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.io.Serializable;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public abstract class ServerRequest implements Serializable {
        
    public abstract void handleMessage(MageServer server, ChannelHandlerContext ctx);
    
}
