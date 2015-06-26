package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SendPlayerIntegerRequest extends ServerRequest {
    private final UUID gameId;
    private final int i;

    public SendPlayerIntegerRequest(UUID gameId, int i) {
        this.gameId = gameId;
        this.i = i;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerInteger(gameId, ctx.channel().id().asLongText(), i);
    }
    
}
