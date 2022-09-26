package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.UUIDResponse;

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
        ctx.writeAndFlush(new UUIDResponse(server.getTournamentChatId(getSessionId(ctx), tournamentId))).addListener(WriteListener.getInstance());
    }
    
}
