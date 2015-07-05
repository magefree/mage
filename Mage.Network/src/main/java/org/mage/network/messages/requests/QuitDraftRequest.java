package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class QuitDraftRequest extends ServerRequest {
    private final UUID draftId;

    public QuitDraftRequest(UUID draftId) {
        this.draftId = draftId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.quitDraft(draftId, getSessionId(ctx));
    }
    
}
