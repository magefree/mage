package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.interfaces.MageServer;

/**
 *
 * @author BetaSteward
 */
public class QuitTournamentRequest extends ServerRequest {
    private final UUID tournamentId;

    public QuitTournamentRequest(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        server.quitTournament(tournamentId, getSessionId(ctx));
    }
    
}
