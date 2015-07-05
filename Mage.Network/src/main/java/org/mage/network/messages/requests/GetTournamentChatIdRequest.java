package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.UUIDResponse;

/**
 *
 * @author BetaSteward
 */
public class GetTournamentChatIdRequest extends ServerRequest {
    private final UUID tournamentId;

    public GetTournamentChatIdRequest(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new UUIDResponse(server.getTournamentChatId(tournamentId))).addListener(WriteListener.getInstance());
    }
    
}
