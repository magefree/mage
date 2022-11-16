package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

public class SetBoosterLoadedRequest extends ServerRequest {
    private final UUID draftId;

    public SetBoosterLoadedRequest(UUID draftId) {
        this.draftId = draftId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.setBoosterLoaded(draftId, getSessionId(ctx));
    }
    
}
