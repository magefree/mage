package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.CreateTableMessage;
import org.mage.network.model.CreateTableRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class TableMessageHandler extends SimpleChannelInboundHandler<CreateTableRequest> {
            
    private final MageServer server;
    
    public TableMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, CreateTableRequest msg) {
        ctx.writeAndFlush(new CreateTableMessage(server.createTable(ctx.channel().id().asLongText(), msg.getRoomId(), msg.getMatchOptions())));        
    }

}
