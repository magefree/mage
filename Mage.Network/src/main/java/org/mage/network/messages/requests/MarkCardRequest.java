package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class MarkCardRequest extends ServerRequest {
    private final UUID draftId;
    private final UUID cardId;

    public MarkCardRequest(UUID draftId, UUID cardId) {
        this.draftId = draftId;
        this.cardId = cardId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.markCard(draftId, getSessionId(ctx), cardId);
    }
    
}
