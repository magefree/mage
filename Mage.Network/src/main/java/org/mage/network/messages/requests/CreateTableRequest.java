package org.mage.network.messages.requests;

import io.netty.channel.ChannelHandlerContext;
import java.util.UUID;
import mage.game.match.MatchOptions;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageServer;
import org.mage.network.messages.responses.CreateTableMessage;

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
        ctx.writeAndFlush(new CreateTableMessage(server.createTable(ctx.channel().id().asLongText(), roomId, options))).addListener(WriteListener.getInstance());
    }
    
}
