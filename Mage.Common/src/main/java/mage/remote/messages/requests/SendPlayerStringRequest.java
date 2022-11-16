package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SendPlayerStringRequest extends ServerRequest {
    private final UUID gameId;
    private final String string;

    public SendPlayerStringRequest(UUID gameId, String string) {
        this.gameId = gameId;
        this.string = string;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerString(gameId, getSessionId(ctx), string);
    }
    
}
