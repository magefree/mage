package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class JoinTournamentRequest extends ServerRequest {
    private final UUID tournamentId;

    public JoinTournamentRequest(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new BooleanResponse(server.joinTournament(tournamentId, getSessionId(ctx)))).addListener(WriteListener.getInstance());
    }
    
}
