package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.interfaces.MageServer;

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
