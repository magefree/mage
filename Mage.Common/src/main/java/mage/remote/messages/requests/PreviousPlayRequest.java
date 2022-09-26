package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

public class PreviousPlayRequest extends ServerRequest {
    private final UUID gameId;

    public PreviousPlayRequest(UUID gameId) {
        this.gameId = gameId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.previousPlay(gameId, getSessionId(ctx));
    }
    
}
