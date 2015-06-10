package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.TableWaitingMessage;
import org.mage.network.model.TableWaitingRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class TableWaitingMessageHandler extends SimpleChannelInboundHandler<TableWaitingRequest> {
            
    private final MageServer server;
    
    public TableWaitingMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, TableWaitingRequest msg) {
        ctx.writeAndFlush(new TableWaitingMessage(server.getTable(msg.getRoomId(), msg.getTableId())));        
    }

}
