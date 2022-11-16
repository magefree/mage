package mage.remote.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.game.match.MatchOptions;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.responses.TableViewResponse;

/**
 *
 * @author BetaSteward
 */
public class CreateTableRequest extends ServerRequest {
    
    private UUID roomId;
    private MatchOptions options;
    
    public CreateTableRequest(UUID roomId, MatchOptions options) {
        this.roomId = roomId;
        this.options = options;
    }
    
    @Override
    public void handleMessage(MageServer server, ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TableViewResponse(server.createTable(getSessionId(ctx), roomId, options))).addListener(WriteListener.getInstance());
    }
    
}
