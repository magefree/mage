package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.BooleanResponse;

/**
 *
 * @author BetaSteward
 */
public class StartTournamentRequest extends ServerRequest {
    private final UUID roomId;
    private final UUID tableId;

    public StartTournamentRequest(UUID roomId, UUID tableId) {
        this.roomId = roomId;
        this.tableId = tableId;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new BooleanResponse(server.startTournament(getSessionId(ctx), roomId, tableId))).addListener(WriteListener.getInstance());
    }
    
}
