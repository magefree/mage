package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class JoinDraftRequest extends ServerRequest {
    private final UUID draftId;

    public JoinDraftRequest(UUID draftId) {
        this.draftId = draftId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.joinDraft(draftId, getSessionId(ctx));
    }
    
}
