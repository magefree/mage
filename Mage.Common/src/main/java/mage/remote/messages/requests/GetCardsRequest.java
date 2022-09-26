package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.GetCardsResponse;

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
