package mage.remote.messages.requests;

import mage.remote.messages.responses.GetTournamentResponse;
import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;

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
        ctx.writeAndFlush(new GetTournamentResponse(server.getTournament(getSessionId(ctx), tournamentId))).addListener(WriteListener.getInstance());
    }
    
}
