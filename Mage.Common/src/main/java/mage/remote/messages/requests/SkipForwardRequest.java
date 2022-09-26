package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

public class SkipForwardRequest extends ServerRequest {
    private final UUID gameId;
    private final int i;

    public SkipForwardRequest(UUID gameId, int i) {
        this.gameId = gameId;
        this.i = i;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.skipForward(gameId, getSessionId(ctx), i);
    }
}
