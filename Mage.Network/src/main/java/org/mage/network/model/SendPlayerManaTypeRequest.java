package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.constants.ManaType;
import org.mage.network.interfaces.MageServer;

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
        server.sendPlayerManaType(gameId, playerId, ctx.channel().id().asLongText(), manaType);
    }
    
}
