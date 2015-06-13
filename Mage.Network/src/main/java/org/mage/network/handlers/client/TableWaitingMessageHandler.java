package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.view.TableView;
import org.mage.network.handlers.WriteListener;
import org.mage.network.model.TableWaitingMessage;
import org.mage.network.model.TableWaitingRequest;


/**
 *
 * @author BetaSteward
 */
public class TableWaitingMessageHandler extends SimpleChannelInboundHandler<TableWaitingMessage> {

    private ChannelHandlerContext ctx;
    private final BlockingQueue<TableView> queue = new LinkedBlockingQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TableWaitingMessage msg) throws Exception {
        queue.offer(msg.getRoom());
    }
    
    public TableView getTable(UUID roomId, UUID tableId) throws Exception {
        queue.clear();
        ctx.writeAndFlush(new TableWaitingRequest(roomId, tableId)).addListener(WriteListener.getInstance());
        return queue.take();
    }
    
}
