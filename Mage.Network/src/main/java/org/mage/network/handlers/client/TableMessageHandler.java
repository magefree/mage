package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.game.match.MatchOptions;
import mage.view.TableView;
import org.mage.network.handlers.WriteListener;
import org.mage.network.model.CreateTableMessage;
import org.mage.network.model.CreateTableRequest;
import org.mage.network.model.RemoveTableRequest;
import org.mage.network.model.SwapSeatRequest;


/**
 *
 * @author BetaSteward
 */
public class TableMessageHandler extends SimpleChannelInboundHandler<CreateTableMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<TableView> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, CreateTableMessage msg) throws Exception {
        queue.offer(msg.getTable());
    }
    
    public TableView createTable(UUID roomId, MatchOptions options) throws Exception {
        queue.clear();
        ctx.writeAndFlush(new CreateTableRequest(roomId, options)).addListener(WriteListener.getInstance());
        return queue.take();
    }

    public void removeTable(UUID roomId, UUID tableId) throws Exception {
        ctx.writeAndFlush(new RemoveTableRequest(roomId, tableId)).addListener(WriteListener.getInstance());
    }

    public void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws Exception {
        ctx.writeAndFlush(new SwapSeatRequest(roomId, tableId, seatNum1, seatNum2)).addListener(WriteListener.getInstance());
    }

}
