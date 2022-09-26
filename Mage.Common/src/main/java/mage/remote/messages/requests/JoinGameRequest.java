package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.UUIDResponse;

/**
 *
 * @author BetaSteward
 */
public class JoinGameRequest extends ServerRequest {
    
    private UUID gameId;
    
    public JoinGameRequest(UUID gameId) {
        this.gameId = gameId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new UUIDResponse(server.joinGame(gameId, getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }

}
