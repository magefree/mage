package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SendPlayerBooleanRequest extends ServerRequest {
    private final UUID gameId;
    private final boolean b;

    public SendPlayerBooleanRequest(UUID gameId, boolean b) {
        this.gameId = gameId;
        this.b = b;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerBoolean(gameId, ctx.channel().id().asLongText(), b);
    }
    
}
