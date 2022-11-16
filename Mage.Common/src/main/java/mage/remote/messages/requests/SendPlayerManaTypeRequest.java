package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.constants.ManaType;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class SendPlayerManaTypeRequest extends ServerRequest {
    private final UUID gameId;
    private final UUID playerId;
    private final ManaType manaType;

    public SendPlayerManaTypeRequest(UUID gameId, UUID playerId, ManaType manaType) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.manaType = manaType;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerManaType(gameId, playerId, getSessionId(ctx), manaType);
    }
    
}
