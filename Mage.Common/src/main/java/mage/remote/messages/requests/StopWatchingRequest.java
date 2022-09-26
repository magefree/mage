package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class StopWatchingRequest extends ServerRequest {
    private final UUID gameId;

    public StopWatchingRequest(UUID gameId) {
        this.gameId = gameId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.stopWatching(gameId, getSessionId(ctx));
    }
    
}
