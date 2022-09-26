package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.PickCardResponse;

/**
 *
 * @author BetaSteward
 */
public class PickCardRequest extends ServerRequest {
    private final UUID draftId;
    private final UUID cardId;
    private final Set<UUID> cardsHidden = new HashSet<>();

    public PickCardRequest(UUID draftId, UUID cardId, Set<UUID> cardsHidden) {
        this.draftId = draftId;
        this.cardId = cardId;
        this.cardsHidden.addAll(cardsHidden);
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new PickCardResponse(server.pickCard(draftId, getSessionId(ctx), cardId, cardsHidden))).addListener(WriteListener.getInstance());
    }
    
}
