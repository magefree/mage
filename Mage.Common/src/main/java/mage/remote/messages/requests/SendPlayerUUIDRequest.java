package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SendPlayerUUIDRequest extends ServerRequest {
    private final UUID gameId;
    private final UUID id;

    public SendPlayerUUIDRequest(UUID gameId, UUID id) {
        this.gameId = gameId;
        this.id = id;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerUUID(gameId, getSessionId(ctx), id);
    }
    
}
