package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class QuitMatchRequest extends ServerRequest {
    private final UUID gameId;

    public QuitMatchRequest(UUID gameId) {
        this.gameId = gameId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.quitMatch(gameId, getSessionId(ctx));
    }
    
}
