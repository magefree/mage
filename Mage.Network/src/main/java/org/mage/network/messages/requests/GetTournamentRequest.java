package org.mage.network.messages.requests;

import org.mage.network.messages.responses.GetTournamentResponse;
import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class GetTournamentRequest extends ServerRequest {
    private final UUID tournamentId;

    public GetTournamentRequest(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new GetTournamentResponse(server.getTournament(tournamentId))).addListener(WriteListener.getInstance());
    }
    
}
