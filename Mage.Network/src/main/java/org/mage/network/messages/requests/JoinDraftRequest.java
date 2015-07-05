package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.BooleanResponse;

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
        ctx.writeAndFlush(new BooleanResponse(server.joinDraft(draftId, getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }
    
}
