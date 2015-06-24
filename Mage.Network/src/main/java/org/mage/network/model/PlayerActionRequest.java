package org.mage.network.model;

import io.netty.channel.ChannelHandlerContext;
import java.io.Serializable;
import java.util.UUID;
import mage.constants.PlayerAction;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class PlayerActionRequest extends ServerRequest {
    
    private final PlayerAction playerAction;
    private final UUID gameId;
    private final Serializable data;

    public PlayerActionRequest(PlayerAction playerAction, UUID gameId, Serializable data) {
        this.playerAction = playerAction;
        this.gameId = gameId;
        this.data = data;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.sendPlayerAction(playerAction, gameId, ctx.channel().id().asLongText(), data);
    }
    
}
