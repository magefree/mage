package org.mage.network.handlers.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mage.network.interfaces.MageServer;
import org.mage.network.model.CreateTableMessage;
import org.mage.network.model.CreateTableRequest;
import org.mage.network.model.JoinTableMessage;
import org.mage.network.model.JoinTableRequest;
import org.mage.network.model.LeaveTableRequest;
import org.mage.network.model.LeftTableMessage;
import org.mage.network.model.RemoveTableRequest;
import org.mage.network.model.SwapSeatRequest;
import org.mage.network.model.TableRequest;
import org.mage.network.model.TableWaitingMessage;
import org.mage.network.model.TableWaitingRequest;

/**
 *
 * @author BetaSteward
 */
@Sharable
public class TableMessageHandler extends SimpleChannelInboundHandler<TableRequest> {
            
    private final MageServer server;
    
    public TableMessageHandler (MageServer server) {
        this.server = server;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, TableRequest msg) {
        if (msg instanceof TableWaitingRequest) {
            ctx.writeAndFlush(new TableWaitingMessage(server.getTable(msg.getRoomId(), msg.getTableId())));        
        }
        if (msg instanceof RemoveTableRequest) {
            server.removeTable(ctx.channel().id().asLongText(), msg.getRoomId(), msg.getTableId());
        }
        if (msg instanceof LeaveTableRequest) {
            ctx.writeAndFlush(new LeftTableMessage(server.leaveTable(ctx.channel().id().asLongText(), msg.getRoomId(), msg.getTableId())));        
        }
        if (msg instanceof JoinTableRequest) {
            JoinTableRequest r = (JoinTableRequest)msg;
            ctx.writeAndFlush(new JoinTableMessage(server.joinTable(ctx.channel().id().asLongText(), r.getRoomId(), r.getTableId(), r.getName(), r.getPlayerType(), r.getSkill(), r.getDeckCardLists(), r.getPassword())));
        }
        if (msg instanceof SwapSeatRequest) {
            SwapSeatRequest r = (SwapSeatRequest)msg;
            server.swapSeats(ctx.channel().id().asLongText(), r.getRoomId(), r.getTableId(), r.getSeatNum1(), r.getSeatNum2());
        }
    }

}
