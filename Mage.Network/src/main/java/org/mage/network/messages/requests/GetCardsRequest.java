package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.GetCardsResponse;

/**
 *
 * @author BetaSteward
 */
public class GetCardsRequest extends ServerRequest {

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetCardsResponse(server.getCards())).addListener(WriteListener.getInstance());
    }
    
}
