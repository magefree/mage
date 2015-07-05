package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.game.tournament.TournamentOptions;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.TableViewResponse;

/**
 *
 * @author BetaSteward
 */
public class CreateTournamentRequest extends ServerRequest {
    private final UUID roomId;
    private final TournamentOptions options;

    public CreateTournamentRequest(UUID roomId, TournamentOptions options) {
        this.roomId = roomId;
        this.options = options;
    }

    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TableViewResponse(server.createTournamentTable(getSessionId(ctx), roomId, options))).addListener(WriteListener.getInstance());
    }
    
}
